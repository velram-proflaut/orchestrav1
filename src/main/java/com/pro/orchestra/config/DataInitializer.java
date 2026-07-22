package com.pro.orchestra.config;

import com.pro.orchestra.entity.AppGroup;
import com.pro.orchestra.entity.AppRole;
import com.pro.orchestra.repository.AppGroupRepository;
import com.pro.orchestra.repository.AppRoleRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


    private final AppRoleRepository roleRepository;

    private final AppGroupRepository groupRepository;


    @Override
    public void run(String... args) {


        createRole("ADMIN");
        createRole("MANAGER");
        createRole("EMPLOYEE");


        createGroup("HR");
        createGroup("Finance");
        createGroup("Management");
    }


    private void createRole(String name) {

        if(!roleRepository.existsByName(name)) {

            AppRole role = new AppRole();

            role.setName(name);

            roleRepository.save(role);
        }
    }


    private void createGroup(String name) {

        if(!groupRepository.existsByName(name)) {

            AppGroup group = new AppGroup();

            group.setName(name);

            groupRepository.save(group);
        }
    }

}
