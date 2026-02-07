package com.cau.artchive.controller;

import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 아이디 검색 시 닉네임 리턴 (친구추가용)
    @GetMapping("/search/{userId}")
    public ApiResponse<String> getNicknameById(@PathVariable String userId) {
        String nickname = userService.getNickname(userId);
        return ApiResponse.success(nickname, "USER_SEARCH_SUCCESS");
    }
}