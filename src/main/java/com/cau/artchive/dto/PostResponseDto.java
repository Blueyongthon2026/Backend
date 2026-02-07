package com.cau.artchive.dto;

import com.cau.artchive.entity.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {
    private Long postId;
    private String authorUserId;
    private String authorNickname;
    private String coverImage;
    private int likeCount;
    private Category category;
    private String workName;
    private String title;
    private String content;
    private String location;
    private Double rating;
    private LocalDate viewingDate;
    private LocalDateTime createdAt;
}