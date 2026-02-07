package com.cau.artchive.repository;

import com.cau.artchive.entity.Follow;
import com.cau.artchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 나(from)와 상대방(to)의 존재 여부 확인
    boolean existsByFromUserAndToUser(User fromUser, User toUser);

    // 언팔로우 (관계 삭제)
    void deleteByFromUserAndToUser(User fromUser, User toUser);
}