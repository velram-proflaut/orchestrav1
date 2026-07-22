package com.pro.orchestra.service;

import com.pro.orchestra.entity.AppUser;
import com.pro.orchestra.entity.WorkflowProcess;
import com.pro.orchestra.enums.WorkflowPermission;
import com.pro.orchestra.repository.WorkflowProcessGroupRepository;
import com.pro.orchestra.repository.WorkflowProcessUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkflowPermissionService {


    private final WorkflowProcessUserRepository userRepository;
    private final WorkflowProcessGroupRepository groupRepository;


    public boolean canView(
            WorkflowProcess process,
            AppUser user) {


        // EDIT users can also VIEW

        if(canEdit(process,user)){
            return true;
        }


        return userRepository
                .existsByProcessAndUserAndPermission(
                        process,
                        user,
                        WorkflowPermission.VIEW)
                ||
                user.getGroups()
                        .stream()
                        .anyMatch(group ->
                                groupRepository
                                        .existsByProcessAndGroupAndPermission(
                                                process,
                                                group,
                                                WorkflowPermission.VIEW));
    }



    public boolean canEdit(
            WorkflowProcess process,
            AppUser user) {


        return userRepository
                .existsByProcessAndUserAndPermission(
                        process,
                        user,
                        WorkflowPermission.EDIT)
                ||
                user.getGroups()
                        .stream()
                        .anyMatch(group ->
                                groupRepository
                                        .existsByProcessAndGroupAndPermission(
                                                process,
                                                group,
                                                WorkflowPermission.EDIT));
    }

}