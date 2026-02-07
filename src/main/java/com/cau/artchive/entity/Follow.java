package com.cau.artchive.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followRelationsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_dbid")
    private User fromUser; // 팔로우를 한 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_dbid")
    private User toUser;   // 팔로우를 받은 사람

    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Follow(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}