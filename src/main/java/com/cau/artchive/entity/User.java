package com.cau.artchive.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid; //TODO dbid -> dbId 로 나중에 리팩토링하기!!! 지금은 말고 ( + "dbid" 로 전체검색도 필수 )

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    private String nickname;

    @Builder
    public User(String userId, String password, String nickname) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
    }

    public void updateUserId(String newUserId) {
        this.userId = newUserId;
    }
}