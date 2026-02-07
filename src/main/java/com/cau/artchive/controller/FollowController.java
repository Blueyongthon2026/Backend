package com.cau.artchive.controller;

import com.cau.artchive.entity.User;
import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.jwt.AuthUtils;
import com.cau.artchive.service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/{toUserId}")
    public ApiResponse<Integer> toggleFollow(
            @PathVariable String toUserId,
            HttpServletRequest request
    ) {
        User me = AuthUtils.getUser(request);
        boolean following = followService.toggleFollow(me, toUserId);
        return ApiResponse.success(following ? 1 : 0, "FOLLOW_TOGGLE_SUCCESS");
    }
}
