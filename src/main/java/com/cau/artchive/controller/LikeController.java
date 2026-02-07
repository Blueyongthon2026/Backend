package com.cau.artchive.controller;

import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.service.LikeService;
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
    public ApiResponse<Integer> toggleLike(@PathVariable Long postId) {
        boolean isLiked = likeService.toggleLike(postId);

        // true면 1, false면 0으로 변환해서 반환 (또는 boolean 그대로 반환해도 무방함)
        int result = isLiked ? 1 : 0;

        return ApiResponse.success(result, "LIKE_TOGGLE_SUCCESS");
    }
}