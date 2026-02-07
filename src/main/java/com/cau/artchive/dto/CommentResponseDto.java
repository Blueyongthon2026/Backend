package com.cau.artchive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// 댓글 응답용 DTO
@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String authorUserId;
    private String authorNickname;
    private String content;
    private LocalDateTime createdAt;
}