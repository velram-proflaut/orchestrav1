package com.pro.orchestra.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public String getCurrentUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authentication = " + authentication);
        System.out.println("Principal = " + authentication.getPrincipal());
        System.out.println("Authorities = " + authentication.getAuthorities());
        return authentication.getName();
    }
}
