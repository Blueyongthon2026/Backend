package com.cau.artchive.service;

import com.cau.artchive.entity.Like;
import com.cau.artchive.entity.Post;
import com.cau.artchive.entity.User;
import com.cau.artchive.repository.LikeRepository;
import com.cau.artchive.repository.PostRepository;
import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void toggleLike(Long postId) {
        // TODO: JWT 토큰의 유저 정보로 대체 필요
        User user = userRepository.findByUserId("adminid").orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        if (likeRepository.existsByUserAndPost(user, post)) {
            likeRepository.deleteByUserAndPost(user, post); // 직접 삭제 메서드 활용
            post.updateLikeCount(false);
        } else {
            likeRepository.save(Like.builder().user(user).post(post).build());
            post.updateLikeCount(true);
        }
    }
}