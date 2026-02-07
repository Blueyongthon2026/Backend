package com.cau.artchive.controller;

import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // POST /api/v1/follow/user2 형태 호출
    @PostMapping("/{toUserId}")
    public ApiResponse<Integer> toggleFollow(@PathVariable String toUserId) {
        boolean isFollowing = followService.toggleFollow(toUserId);

        // true면 1(팔로잉 중), false면 0(팔로우 안 함)
        int result = isFollowing ? 1 : 0;

        return ApiResponse.success(result, "FOLLOW_TOGGLE_SUCCESS");
    }
}