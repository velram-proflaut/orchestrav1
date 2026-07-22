package com.pro.orchestra.repository;

import com.pro.orchestra.entity.WorkflowProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkflowProcessRepository extends JpaRepository<WorkflowProcess, Long> {

    Optional<WorkflowProcess> findByProcessKey(String processKey);

    Optional<WorkflowProcess> findByProcessKeyAndActiveTrue(String processKey);

    Optional<WorkflowProcess> findByProcessKeyAndVersion(
            String processKey,
            Integer version);
}