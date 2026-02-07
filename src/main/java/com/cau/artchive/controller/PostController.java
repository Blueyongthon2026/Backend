package com.cau.artchive.controller;

import com.cau.artchive.dto.PostDetailResponseDto;
import com.cau.artchive.dto.PostRequestDto;
import com.cau.artchive.dto.PostResponseDto;
import com.cau.artchive.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 이미지 파일을 받지 않고 JSON 데이터만 받음
    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostRequestDto requestDto) {
        return ResponseEntity.ok(postService.createPost(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostDetail(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto dto) {
        postService.updatePost(postId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> searchPosts(@RequestParam String keyword) {
        return ResponseEntity.ok(postService.searchPosts(keyword));
    }
}