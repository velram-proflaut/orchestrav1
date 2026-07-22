package com.pro.orchestra.entity;

import com.pro.orchestra.enums.WorkflowPermission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "workflow_process_user")
@Getter
@Setter
public class WorkflowProcessUser extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private WorkflowProcess process;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkflowPermission permission;

}