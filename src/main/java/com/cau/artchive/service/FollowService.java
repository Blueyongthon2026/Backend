package com.cau.artchive.service;

import com.cau.artchive.entity.Follow;
import com.cau.artchive.entity.User;
import com.cau.artchive.repository.FollowRepository;
import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    // 팔로우 하기
    @Transactional
    public void follow(Long fromDbid, Long toDbid) {
        if (fromDbid.equals(toDbid)) throw new IllegalArgumentException("자기 자신은 팔로우할 수 없습니다.");

        User fromUser = userRepository.findById(fromDbid).orElseThrow();
        User toUser = userRepository.findById(toDbid).orElseThrow();

        if (!followRepository.existsByFromUserAndToUser(fromUser, toUser)) {
            followRepository.save(new Follow(fromUser, toUser));
        }
    }

    // 내가 팔로우하고 있는 사람들 리스트 조회
//    public List<UserResponseDto> getFollowingList(Long myDbid) {
//        List<Follow> follows = followRepository.findAllByFromUserWithToUser(myDbid);
//        return follows.stream()
//                .map(f -> new UserResponseDto(f.getToUser())) // DTO로 변환
//                .collect(Collectors.toList());
//    }
}
