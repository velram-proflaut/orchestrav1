package com.pro.orchestra.service;

import com.pro.orchestra.dto.StartProcessRequest;
import com.pro.orchestra.dto.StartProcessResponse;
import com.pro.orchestra.dto.WorkflowDefinitionResponse;
import com.pro.orchestra.entity.AppUser;
import com.pro.orchestra.entity.WorkflowProcess;
import com.pro.orchestra.repository.AppUserRepository;
import com.pro.orchestra.repository.WorkflowProcessRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final WorkflowProcessRepository workflowProcessRepository;
    private final AppUserRepository appUserRepository;
    private final BusinessKeyService businessKeyService;
    private final WorkflowPermissionService workflowPermissionService;

    @Transactional
    public WorkflowProcess deploy(MultipartFile file) throws IOException {

        // Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Workflow file is empty.");
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null || !fileName.endsWith(".bpmn20.xml")) {
            throw new IllegalArgumentException(
                    "Only .bpmn20.xml files are supported.");
        }

        // Deploy to Flowable
        Deployment deployment = repositoryService.createDeployment()
                .name(fileName)
                .addInputStream(fileName, file.getInputStream())
                .deploy();

        // Get deployed process definition
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();

        if (processDefinition == null) {
            throw new RuntimeException(
                    "No process definition found after deployment.");
        }

        // Deactivate previous active version
        workflowProcessRepository
                .findByProcessKeyAndActiveTrue(processDefinition.getKey())
                .ifPresent(existing -> {
                    existing.setActive(false);
                    workflowProcessRepository.save(existing);
                });

        // Save latest version
        WorkflowProcess workflowProcess = new WorkflowProcess();
        workflowProcess.setProcessKey(processDefinition.getKey());
        workflowProcess.setProcessName(processDefinition.getName());
        workflowProcess.setProcessDefinitionId(processDefinition.getId());
        workflowProcess.setDeploymentId(deployment.getId());
        workflowProcess.setVersion(processDefinition.getVersion());
        workflowProcess.setActive(true);

        return workflowProcessRepository.save(workflowProcess);
    }

    @Transactional
    public StartProcessResponse startProcess(
            String processKey,
            String userId,
            StartProcessRequest request) throws AccessDeniedException {


        AppUser user = appUserRepository.findByUsername(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        WorkflowProcess process =
                workflowProcessRepository
                        .findByProcessKey(processKey)
                        .orElseThrow(() ->
                                new RuntimeException("Workflow not found"));

        if(!workflowPermissionService.canEdit(process,user)) {

            throw new AccessDeniedException(
                    "User does not have edit permission");
        }

        String businessKey =
                businessKeyService.generate(processKey);

        ProcessInstance instance =
                runtimeService.startProcessInstanceByKey(
                        processKey,
                        businessKey,
                        request.getVariables());

        return StartProcessResponse.builder()
                .processInstanceId(instance.getProcessInstanceId())
                .processDefinitionId(instance.getProcessDefinitionId())
                .businessKey(businessKey)
                .status("RUNNING")
                .build();
    }

    public List<WorkflowDefinitionResponse> getDefinitions() {

        List<ProcessDefinition> definitions = repositoryService
                .createProcessDefinitionQuery()
                .latestVersion()
                .list();

        return definitions.stream().map(definition -> {

            WorkflowProcess workflowProcess =
                    workflowProcessRepository
                            .findByProcessKeyAndVersion(
                                    definition.getKey(),
                                    definition.getVersion())
                            .orElse(null);

            return WorkflowDefinitionResponse.builder()
                    .workflowProcessId(
                            workflowProcess != null ? workflowProcess.getId() : null)
                    .processKey(definition.getKey())
                    .processName(definition.getName())
                    .version(definition.getVersion())
                    .processDefinitionId(definition.getId())
                    .deploymentId(definition.getDeploymentId())
                    .active(
                            workflowProcess != null
                                    ? workflowProcess.getActive()
                                    : false)
                    .build();

        }).toList();
    }
}