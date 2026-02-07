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

    public void toggleFollow(String toUserId) {
        // TODO: 현재는 "adminid"로 하드코딩. 나중에 JWT 토큰에서 추출한 내 ID를 사용해야 함.
        User me = userRepository.findByUserId("adminid")
                .orElseThrow(() -> new RuntimeException("내 계정을 찾을 수 없습니다."));

        // 내가 팔로우하려는 상대방
        User targetUser = userRepository.findByUserId(toUserId)
                .orElseThrow(() -> new RuntimeException("팔로우할 대상을 찾을 수 없습니다."));

        if (me.getUserId().equals(targetUser.getUserId())) {
            throw new RuntimeException("자기 자신은 팔로우할 수 없습니다.");
        }

        if (followRepository.existsByFromUserAndToUser(me, targetUser)) {
            // 이미 팔로우 중이면 취소 (언팔로우)
            followRepository.deleteByFromUserAndToUser(me, targetUser);
        } else {
            // 팔로우 신청
            followRepository.save(Follow.builder()
                    .fromUser(me)
                    .toUser(targetUser)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
    }
}