package com.pro.orchestra.controller;

import com.pro.orchestra.dto.StartProcessRequest;
import com.pro.orchestra.dto.StartProcessResponse;
import com.pro.orchestra.dto.WorkflowDefinitionResponse;
import com.pro.orchestra.entity.WorkflowProcess;
import com.pro.orchestra.service.WorkflowService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/workflow")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class WorkflowController {

    private final WorkflowService workflowService;

    @PostMapping(value = "/deploy", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WorkflowProcess> deploy(
            @RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok(workflowService.deploy(file));
    }

    @GetMapping("/definitions")
    public ResponseEntity<List<WorkflowDefinitionResponse>> definitions() {

        return ResponseEntity.ok(workflowService.getDefinitions());
    }

    @PostMapping("/process/start/{processKey}")
    public ResponseEntity<StartProcessResponse> startProcess(
            @PathVariable String processKey,
            @RequestBody StartProcessRequest request,
            Authentication authentication) throws AccessDeniedException {

        UserDetails jwtUser = (UserDetails) authentication.getPrincipal();



        return ResponseEntity.ok(
                workflowService.startProcess(
                        processKey,
                        jwtUser.getUsername(),
                        request));
    }
}