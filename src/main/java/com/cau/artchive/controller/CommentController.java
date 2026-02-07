package com.cau.artchive.controller;

import com.cau.artchive.dto.CommentRequestDto;
import com.cau.artchive.dto.CommentResponseDto;
import com.cau.artchive.entity.User;
import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.cau.artchive.jwt.AuthUtils;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ApiResponse<Long> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest request
    ) {
        User user = AuthUtils.getUser(request);
        return ApiResponse.success(
                commentService.createComment(user, postId, dto),
                "COMMENT_CREATE_SUCCESS"
        );
    }

    @PutMapping("/{commentId}")
    public ApiResponse<Long> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest request
    ) {
        User user = AuthUtils.getUser(request);
        return ApiResponse.success(
                commentService.updateComment(user, commentId, dto),
                "COMMENT_UPDATE_SUCCESS"
        );
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Long> deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest request
    ) {
        User user = AuthUtils.getUser(request);
        commentService.deleteComment(user, commentId);
        return ApiResponse.success(commentId, "COMMENT_DELETE_SUCCESS");
    }

    @GetMapping("/post/{postId}")
    public ApiResponse<List<CommentResponseDto>> getCommentsByPost(
            @PathVariable Long postId
    ) {
        return ApiResponse.success(
                commentService.getCommentsByPost(postId),
                "COMMENT_LIST_SUCCESS"
        );
    }
}
