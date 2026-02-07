package com.cau.artchive.service;

import com.cau.artchive.dto.LoginRequest;
import com.cau.artchive.dto.SignupRequest;
import com.cau.artchive.entity.User;
import com.cau.artchive.global.config.PasswordConfig;
import com.cau.artchive.global.exception.BusinessException;
import com.cau.artchive.global.exception.ErrorCode;
import com.cau.artchive.jwt.JwtProvider;
import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public String signup(SignupRequest req) {
        if (userRepository.findByUserId(req.getUsername()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_USER);
        }

        User user = User.builder()
                .userId(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .build();

        userRepository.save(user);
        return jwtProvider.createToken(user.getDbid());
    }

    public String login(LoginRequest req) {
        User user = userRepository.findByUserId(req.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        return jwtProvider.createToken(user.getDbid());
    }
}

