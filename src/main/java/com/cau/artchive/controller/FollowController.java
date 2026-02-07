package com.cau.artchive.controller;

import com.cau.artchive.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // POST /api/follow/user2 형태 호출
    @PostMapping("/{toUserId}")
    public ResponseEntity<String> toggleFollow(@PathVariable String toUserId) {
        followService.toggleFollow(toUserId);
        return ResponseEntity.ok("팔로우 상태가 변경되었습니다.");
    }
}