package com.cau.artchive.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_dbid")
    private User user;
    private String coverImage;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String workName, title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String location;

    private Double rating;
    private LocalDate viewingDate;

    private int likeCount = 0;

    @Builder
    public Post(User user, String coverImage, Category category, String workName, String title, String content, String location, Double rating, LocalDate viewingDate) {
        this.user = user;
        this.coverImage = coverImage;
        this.category = category;
        this.workName = workName;
        this.title = title;
        this.content = content;
        this.location = location;
        this.rating = rating;
        this.viewingDate = viewingDate;
    }

    // 좋아요 수 증감 메서드 (나중에 LikeService에서 호출)
    public void updateLikeCount(boolean increment) {
        if (increment) this.likeCount++;
        else if (this.likeCount > 0) this.likeCount--;
    }
}
