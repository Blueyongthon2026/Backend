package com.cau.artchive.service;

import com.cau.artchive.dto.UserResponseDto;
import com.cau.artchive.entity.Follow;
import com.cau.artchive.entity.User;
import com.cau.artchive.repository.FollowRepository;
import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean toggleFollow(String toUserId) {
        // TODO: 현재는 "adminid" 고정, 추후 JWT 토큰 정보로 교체
        User me = userRepository.findByUserId("adminid")
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
        User target = userRepository.findByUserId(toUserId)
                .orElseThrow(() -> new RuntimeException("TARGET_USER_NOT_FOUND"));

        if (me.getUserId().equals(target.getUserId())) {
            throw new RuntimeException("SELF_FOLLOW_NOT_ALLOWED");
        }

        if (followRepository.existsByFromUserAndToUser(me, target)) {
            // 이미 팔로우 중이면 삭제 (언팔로우)
            followRepository.deleteByFromUserAndToUser(me, target);
            return false; // 현재 상태: 팔로우 아님 (0)
        } else {
            // 팔로우 신청
            followRepository.save(Follow.builder()
                    .fromUser(me)
                    .toUser(target)
                    .createdAt(LocalDateTime.now())
                    .build());
            return true; // 현재 상태: 팔로우 중 (1)
        }
    }
}