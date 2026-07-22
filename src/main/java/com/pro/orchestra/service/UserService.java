package com.pro.orchestra.service;

import com.pro.orchestra.dto.CreateUserRequest;
import com.pro.orchestra.dto.UserProfileResponse;
import com.pro.orchestra.dto.UserResponse;
import com.pro.orchestra.entity.AppGroup;
import com.pro.orchestra.entity.AppRole;
import com.pro.orchestra.entity.AppUser;
import com.pro.orchestra.exception.ResourceNotFoundException;
import com.pro.orchestra.repository.AppUserRepository;
import com.pro.orchestra.util.SecurityUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {


    private final SecurityUtils securityUtils;

    private final AppUserRepository userRepository;

    private final RoleService roleService;

    private final GroupService groupService;

    private final PasswordEncoder passwordEncoder;



    public UserResponse createUser(CreateUserRequest request){


        AppUser user = new AppUser();

        user.setUsername(request.getUsername());

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );


        request.getRoles()
                .forEach(role ->
                        user.getRoles()
                                .add(roleService.findByName(role))
                );


        request.getGroups()
                .forEach(group ->
                        user.getGroups()
                                .add(groupService.findByName(group))
                );


        userRepository.save(user);



        return UserResponse.builder()

                .id(user.getId())

                .username(user.getUsername())

                .email(user.getEmail())

                .roles(
                        user.getRoles()
                                .stream()
                                .map(AppRole::getName)
                                .collect(Collectors.toSet())
                )

                .groups(
                        user.getGroups()
                                .stream()
                                .map(AppGroup::getName)
                                .collect(Collectors.toSet())
                )

                .build();
    }

    public UserProfileResponse getCurrentUser() {

        // Option 1 - Using your SecurityUtils
        String username = securityUtils.getCurrentUsername();

        // Option 2 (equivalent)
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String username = authentication.getName();

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(AppRole::getName)
                                .collect(Collectors.toSet())
                )
                .groups(
                        user.getGroups()
                                .stream()
                                .map(AppGroup::getName)
                                .collect(Collectors.toSet())
                )
                .build();
    }

}