package com.pro.orchestra.service;

import com.pro.orchestra.entity.AppGroup;
import com.pro.orchestra.repository.AppGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GroupService {


    private final AppGroupRepository groupRepository;


    public AppGroup findByName(String name){

        return groupRepository.findByName(name)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Group not found "+name
                        )
                );
    }

}