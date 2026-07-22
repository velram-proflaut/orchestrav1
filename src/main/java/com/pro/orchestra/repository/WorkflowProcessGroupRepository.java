package com.pro.orchestra.repository;

import com.pro.orchestra.entity.AppGroup;
import com.pro.orchestra.entity.WorkflowProcess;
import com.pro.orchestra.entity.WorkflowProcessGroup;
import com.pro.orchestra.enums.WorkflowPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowProcessGroupRepository extends JpaRepository<WorkflowProcessGroup, Long> {

    boolean existsByProcessAndGroup(
            WorkflowProcess process,
            AppGroup group);

    boolean existsByProcessAndGroupAndPermission(
            WorkflowProcess process,
            AppGroup group,
            WorkflowPermission permission
    );

}