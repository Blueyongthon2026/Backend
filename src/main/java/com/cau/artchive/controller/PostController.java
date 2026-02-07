package com.cau.artchive.controller;

import com.cau.artchive.dto.PostDetailResponseDto;
import com.cau.artchive.dto.PostRequestDto;
import com.cau.artchive.dto.PostResponseDto;
import com.cau.artchive.entity.User;
import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.jwt.AuthUtils;
import com.cau.artchive.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<Long> createPost(
            @RequestBody PostRequestDto dto,
            HttpServletRequest request
    ) {
        User user = AuthUtils.getUser(request);
        return ApiResponse.success(
                postService.createPost(user, dto),
                "POST_CREATE_SUCCESS"
        );
    }

    @GetMapping
    public ApiResponse<List<PostResponseDto>> getAllPosts() {
        return ApiResponse.success(postService.getAllPosts(), "POST_LIST_SUCCESS");
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto> getPostDetail(@PathVariable Long postId) {
        return ApiResponse.success(
                postService.getPostDetail(postId),
                "POST_DETAIL_SUCCESS"
        );
    }

    @PutMapping("/{postId}")
    public ApiResponse<Long> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto dto,
            HttpServletRequest request
    ) {
        User user = AuthUtils.getUser(request);
        return ApiResponse.success(
                postService.updatePost(user, postId, dto),
                "POST_UPDATE_SUCCESS"
        );
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Long> deletePost(
            @PathVariable Long postId,
            HttpServletRequest request
    ) {
        User user = AuthUtils.getUser(request);
        postService.deletePost(user, postId);
        return ApiResponse.success(postId, "POST_DELETE_SUCCESS");
    }
}
