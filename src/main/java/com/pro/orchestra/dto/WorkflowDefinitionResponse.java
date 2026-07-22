package com.pro.orchestra.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDefinitionResponse {

    private String processKey;
    private String processName;
    private Integer version;
    private String processDefinitionId;
    private String deploymentId;

    // From your DB
    private Boolean active;
    private Long workflowProcessId;
}