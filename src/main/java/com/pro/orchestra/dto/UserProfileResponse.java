package com.pro.orchestra.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserProfileResponse {

    private Long id;

    private String username;

    private String email;

    private Set<String> roles;

    private Set<String> groups;
}