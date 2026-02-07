package com.cau.artchive.controller;

import com.cau.artchive.dto.CommentRequestDto;
import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<Void> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto dto) {
        commentService.updateComment(commentId, dto);
        return ApiResponse.success(null, "COMMENT_UPDATE_SUCCESS");
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.success(null, "COMMENT_DELETE_SUCCESS");
    }
}