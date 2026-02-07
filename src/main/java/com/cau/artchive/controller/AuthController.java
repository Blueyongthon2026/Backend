package com.cau.artchive.controller;

import com.cau.artchive.dto.AuthResponse;
import com.cau.artchive.dto.LoginRequest;
import com.cau.artchive.dto.SignupRequest;
import com.cau.artchive.entity.User;
import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.repository.UserRepository;
import com.cau.artchive.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ApiResponse<?> signup(@RequestBody SignupRequest req) {
        String token = authService.signup(req);
        User user = userRepository.findByUserId(req.getUsername()).get();

        return ApiResponse.success(
                Map.of(
                        "user", new AuthResponse(
                                user.getDbid(),
                                user.getUserId(),
                                user.getNickname()
                        ),
                        "accessToken", token
                ),
                "SIGNUP_SUCCESS"
        );
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest req) {
        String token = authService.login(req);
        User user = userRepository.findByUserId(req.getUsername()).get();

        return ApiResponse.success(
                Map.of(
                        "user", new AuthResponse(
                                user.getDbid(),
                                user.getUserId(),
                                user.getNickname()
                        ),
                        "accessToken", token
                ),
                "LOGIN_SUCCESS"
        );
    }

}

