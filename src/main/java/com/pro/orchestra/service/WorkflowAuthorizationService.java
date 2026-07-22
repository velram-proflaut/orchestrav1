package com.pro.orchestra.service;

import com.pro.orchestra.entity.AppGroup;
import com.pro.orchestra.entity.AppUser;
import com.pro.orchestra.entity.WorkflowProcess;
import com.pro.orchestra.repository.WorkflowProcessGroupRepository;
import com.pro.orchestra.repository.WorkflowProcessRepository;
import com.pro.orchestra.repository.WorkflowProcessUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class WorkflowAuthorizationService {

    private final WorkflowProcessRepository processRepository;
    private final WorkflowProcessUserRepository processUserRepository;
    private final WorkflowProcessGroupRepository processGroupRepository;

    public void validateStartAccess(AppUser user, String processKey) throws AccessDeniedException {

        WorkflowProcess process =
                processRepository.findByProcessKey(processKey)
                        .orElseThrow();

        // Direct user access
        if (processUserRepository.existsByProcessAndUser(process, user)) {
            return;
        }

        // Group access
        for (AppGroup group : user.getGroups()) {

            if (processGroupRepository.existsByProcessAndGroup(process, group)) {
                return;
            }
        }

        throw new AccessDeniedException(
                "User is not authorized to start workflow");
    }

}