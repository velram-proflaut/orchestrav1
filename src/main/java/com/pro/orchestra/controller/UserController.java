package com.pro.orchestra.controller;

import com.pro.orchestra.dto.CreateUserRequest;
import com.pro.orchestra.dto.UserProfileResponse;
import com.pro.orchestra.dto.UserResponse;
import com.pro.orchestra.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    @PostMapping("/create")
    public UserResponse create(
            @RequestBody CreateUserRequest request){

        return userService.createUser(request);

    }

    @GetMapping("/me")
    public UserProfileResponse me() {
        return userService.getCurrentUser();
    }

}