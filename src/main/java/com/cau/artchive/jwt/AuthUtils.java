package com.cau.artchive.jwt;

import com.cau.artchive.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public class AuthUtils {

    public static User getUser(HttpServletRequest request) {
        User user = (User) request.getAttribute(JwtAuthFilter.AUTH_USER);
        if (user == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }
        return user;
    }
}

