package com.cau.artchive.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Follow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followRelationsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_dbid")
    private User fromUser; // 나 (팔로우를 하는 사람)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_dbid")
    private User toUser;   // 상대방 (내가 팔로우하려는 대상)

    private LocalDateTime createdAt = LocalDateTime.now();
}