package com.cau.artchive.controller;

import com.cau.artchive.dto.PostDetailResponseDto;
import com.cau.artchive.dto.PostRequestDto;
import com.cau.artchive.dto.PostResponseDto;
import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts") // 버전 관리 관례에 따라 v1 추가
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ApiResponse<Long> createPost(@RequestBody PostRequestDto dto) {
        Long postId = postService.createPost(dto);
        return ApiResponse.success(postId, "POST_CREATE_SUCCESS");
    }

    @GetMapping
    public ApiResponse<List<PostResponseDto>> getAllPosts() {
        return ApiResponse.success(postService.getAllPosts(), "POST_LIST_SUCCESS");
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponseDto> getPostDetail(@PathVariable Long postId) {
        return ApiResponse.success(postService.getPostDetail(postId), "POST_DETAIL_SUCCESS");
    }

    @GetMapping("/search")
    public ApiResponse<List<PostResponseDto>> searchPosts(@RequestParam String keyword) {
        return ApiResponse.success(postService.searchPosts(keyword), "POST_SEARCH_SUCCESS");
    }

    @PutMapping("/{postId}")
    public ApiResponse<Long> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto dto) {
        Long updatedPostId = postService.updatePost(postId, dto);
        return ApiResponse.success(updatedPostId, "POST_UPDATE_SUCCESS");
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.success(null, "POST_DELETE_SUCCESS");
    }
}