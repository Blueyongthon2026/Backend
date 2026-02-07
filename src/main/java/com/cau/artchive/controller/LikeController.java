package com.cau.artchive.controller;

import com.cau.artchive.entity.User;
import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.jwt.AuthUtils;
import com.cau.artchive.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ApiResponse<Integer> toggleLike(
            @PathVariable Long postId,
            HttpServletRequest request
    ) {
        User user = AuthUtils.getUser(request);
        boolean liked = likeService.toggleLike(user, postId);
        return ApiResponse.success(liked ? 1 : 0, "LIKE_TOGGLE_SUCCESS");
    }
}
