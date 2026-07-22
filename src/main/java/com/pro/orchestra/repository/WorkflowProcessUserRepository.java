package com.pro.orchestra.repository;

import com.pro.orchestra.entity.AppUser;
import com.pro.orchestra.entity.WorkflowProcess;
import com.pro.orchestra.entity.WorkflowProcessUser;
import com.pro.orchestra.enums.WorkflowPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowProcessUserRepository extends JpaRepository<WorkflowProcessUser, Long> {

    boolean existsByProcessAndUser(
            WorkflowProcess process,
            AppUser user);

    boolean existsByProcessAndUserAndPermission(
            WorkflowProcess process,
            AppUser user,
            WorkflowPermission permission
    );
}