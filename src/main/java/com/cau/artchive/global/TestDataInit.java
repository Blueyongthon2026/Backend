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
        // "adminid"라는 유저가 없으면 생성
        if (userRepository.findByUserId("adminid").isEmpty()) {
            userRepository.save(User.builder()
                    .userId("adminid")
                    .password("1234") // 나중에 암호화 적용
                    .nickname("관리자")
                    .build());
        }
    }
}
