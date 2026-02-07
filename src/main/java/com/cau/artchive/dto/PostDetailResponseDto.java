package com.cau.artchive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailResponseDto {
    private PostResponseDto post;        // 게시글 정보 (위에서 만든 Dto 재사용)
    private List<CommentResponseDto> comments; // 댓글 목록
}
