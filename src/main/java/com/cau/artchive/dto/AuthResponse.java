package com.cau.artchive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private String nickname;
}
