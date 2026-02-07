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

    public boolean toggleFollow(User me, String toUserId) {
        User target = userRepository.findByUserId(toUserId).orElseThrow();

        if (me.getDbid().equals(target.getDbid())) {
            throw new RuntimeException("SELF_FOLLOW_NOT_ALLOWED");
        }

        if (followRepository.existsByFromUserAndToUser(me, target)) {
            followRepository.deleteByFromUserAndToUser(me, target);
            return false;
        } else {
            followRepository.save(Follow.builder()
                    .fromUser(me)
                    .toUser(target)
                    .createdAt(LocalDateTime.now())
                    .build());
            return true;
        }
    }
}
