package com.cau.artchive.service;

import com.cau.artchive.dto.PostRequestDto;
import com.cau.artchive.dto.PostResponseDto;
import com.cau.artchive.entity.Post;
import com.cau.artchive.entity.User;
import com.cau.artchive.repository.PostRepository;
import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(PostRequestDto dto) {
        // 1. "adminid" 유저 조회 (로그인 대신 고정값 사용) //TODO jwt
        User adminUser = userRepository.findByUserId("adminid")
                .orElseThrow(() -> new IllegalStateException("관리자 계정이 생성되지 않았습니다."));

        // 2. 요청하신 고정 이미지 URL 사용
        String fixedImageUrl = "https://ibb.co/B512v7xL"; //TODO S3

        // 3. 게시글 저장
        Post post = Post.builder()
                .user(adminUser)
                .coverImage(fixedImageUrl)
                .category(dto.getCategory())
                .workName(dto.getWorkName())
                .title(dto.getTitle())
                .content(dto.getContent())
                .location(dto.getLocation())
                .rating(dto.getRating())
                .viewingDate(dto.getViewingDate())
                .build();

        return postRepository.save(post).getPostId();
    }

    // 목록 조회 시 likeCount도 포함하도록 DTO 변환 로직 확인
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAllWithUser().stream()
                .map(post -> PostResponseDto.builder()
                        .postId(post.getPostId())
                        .authorNickname(post.getUser().getNickname())
                        .title(post.getTitle())
                        .likeCount(post.getLikeCount()) // 추가됨
                        .coverImage(post.getCoverImage())
                        .build())
                .collect(Collectors.toList());
    }
}