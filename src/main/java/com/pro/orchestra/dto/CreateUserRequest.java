package com.pro.orchestra.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateUserRequest {

    private String username;

    private String email;

    private String password;

    private Set<String> roles;

    private Set<String> groups;
}