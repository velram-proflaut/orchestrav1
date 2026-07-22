package com.pro.orchestra.service;

import com.pro.orchestra.entity.AppRole;
import com.pro.orchestra.repository.AppRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService {


    private final AppRoleRepository roleRepository;


    public AppRole findByName(String name){

        return roleRepository.findByName(name)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Role not found "+name
                        )
                );
    }
}