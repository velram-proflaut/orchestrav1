package com.pro.orchestra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "workflow_process")
@Getter
@Setter
public class WorkflowProcess extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String processKey;

    @Column(nullable = false)
    private String processName;

    @Column(nullable = false)
    private String processDefinitionId;

    @Column(nullable = false)
    private String deploymentId;

    @Column(nullable = false)
    private Integer version;

    private Boolean active = true;
}