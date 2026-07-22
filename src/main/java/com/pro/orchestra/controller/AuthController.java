package com.pro.orchestra.controller;

import com.pro.orchestra.dto.*;

import com.pro.orchestra.service.AuthService;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {



    private final AuthService authService;



    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request){


        return authService.login(request);

    }


}