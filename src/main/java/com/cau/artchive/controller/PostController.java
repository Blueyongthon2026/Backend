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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts") // 버전 관리 관례에 따라 v1 추가
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

    // 1. 전체 공개글 최근순 조회
    @GetMapping("/public")
    public ApiResponse<List<PostResponseDto>> getAllPublicPosts() {
        return ApiResponse.success(postService.getAllPublicPosts(), "PUBLIC_POSTS_SUCCESS");
    }

    // 2. 특정 사용자의 공개글 조회
    @GetMapping("/user/{userId}")
    public ApiResponse<List<PostResponseDto>> getUserPublicPosts(@PathVariable String userId) {
        return ApiResponse.success(postService.getUserPublicPosts(userId), "USER_PUBLIC_POSTS_SUCCESS");
    }

    // 3. 내 책장 조회 (모든 글 - 비공개 포함)
    @GetMapping("/my-shelf")
    public ApiResponse<List<PostResponseDto>> getMyShelf() {
        // TODO: JWT 도입 시 현재 로그인한 유저 ID를 넘겨야 함
        return ApiResponse.success(postService.getMyShelf("adminid"), "MY_SHELF_SUCCESS");
    }

    // 5. 특정 날짜(viewingDate)의 내 포스트 조회
    @GetMapping("/my-calendar")
    public ApiResponse<List<PostResponseDto>> getMyPostsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        // TODO: adminid를 현재 유저로 변경 필요
        return ApiResponse.success(postService.getMyPostsByDate("adminid", date), "MY_CALENDAR_SUCCESS");
    }
}