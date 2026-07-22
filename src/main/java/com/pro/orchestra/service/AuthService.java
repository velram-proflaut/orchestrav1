package com.pro.orchestra.service;

import com.pro.orchestra.dto.*;
import com.pro.orchestra.entity.*;

import com.pro.orchestra.repository.AppUserRepository;

import com.pro.orchestra.security.JwtService;


import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class AuthService {



    private final AppUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;



    public LoginResponse login(LoginRequest request){


        AppUser user =
                userRepository
                        .findByUsername(request.getUsername())

                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Invalid username/password"
                                )
                        );


        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())){


            throw new RuntimeException(
                    "Invalid username/password"
            );
        }



        Map<String,Object> claims =
                Map.of(

                        "userId",
                        user.getId(),

                        "email",
                        user.getEmail(),

                        "roles",
                        user.getRoles()
                                .stream()
                                .map(AppRole::getName)
                                .collect(Collectors.toSet()),


                        "groups",
                        user.getGroups()
                                .stream()
                                .map(AppGroup::getName)
                                .collect(Collectors.toSet())

                );



        String token =
                jwtService.generateToken(
                        claims,
                        user.getUsername()
                );


        return LoginResponse.builder()

                .token(token)

                .username(user.getUsername())

                .build();

    }

}