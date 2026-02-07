package com.cau.artchive.service;

import com.cau.artchive.entity.User;
import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    //아이디로 사용자의 닉네임을 조회합니다. (친구 추가 검색용)
    public String getNickname(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        return user.getNickname();
    }
}
