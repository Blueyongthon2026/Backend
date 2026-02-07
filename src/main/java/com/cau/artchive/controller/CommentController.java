package com.cau.artchive.controller;

import com.cau.artchive.dto.CommentRequestDto;
import com.cau.artchive.dto.CommentResponseDto;
import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ApiResponse<Long> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto dto) {
        return ApiResponse.success(commentService.createComment(postId, dto), "COMMENT_CREATE_SUCCESS");
    }

    @PutMapping("/{commentId}")
    public ApiResponse<Long> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto dto) {
        Long updatedCommentId = commentService.updateComment(commentId, dto);
        return ApiResponse.success(updatedCommentId, "COMMENT_UPDATE_SUCCESS");
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Long> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.success(commentId, "COMMENT_DELETE_SUCCESS");
    }

    // GET /api/v1/comments/post/1 형태로 호출
    @GetMapping("/post/{postId}")
    public ApiResponse<List<CommentResponseDto>> getCommentsByPost(@PathVariable Long postId) {
        return ApiResponse.success(commentService.getCommentsByPost(postId), "COMMENT_LIST_SUCCESS");
    }
}