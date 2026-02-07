package com.cau.artchive.dto;

import com.cau.artchive.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String authorUserId;
    private String authorNickname;
    private String coverImage;
    private Category category;
    private String workName;
    private String title;
    private String content;
    private String location;
    private Double rating;
    private LocalDate viewingDate;
    private int likeCount;
    @JsonProperty("isPublic")
    private boolean open;
    //TODO사용자가 이 글을 좋아요 눌렀는지 여부
//    @JsonProperty("isLiked")
//    private boolean liked;

    private LocalDateTime createdAt;
}