package com.cau.artchive.global;

import com.cau.artchive.entity.User;
import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        // 1. 나 (adminid)
        if (userRepository.findByUserId("adminid").isEmpty()) {
            userRepository.save(User.builder().userId("adminid").password("1234").nickname("관리자").build());
        }

        // 2. 팔로우 대상 (user2)
        if (userRepository.findByUserId("user2").isEmpty()) {
            userRepository.save(User.builder().userId("user2").password("1234").nickname("테스트유저2").build());
        }
    }
}