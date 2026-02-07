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
    public boolean toggleLike(Long postId) {
        // TODO: JWT 도입 시 SecurityContext에서 내 정보를 가져와야 함
        User user = userRepository.findByUserId("adminid").orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        if (likeRepository.existsByUserAndPost(user, post)) {
            // 이미 있다면 삭제 (좋아요 취소)
            likeRepository.deleteByUserAndPost(user, post);
            post.updateLikeCount(false);
            return false; // 현재 상태: 좋아요 아님 (0)
        } else {
            // 없다면 생성 (좋아요 등록)
            likeRepository.save(Like.builder().user(user).post(post).build());
            post.updateLikeCount(true);
            return true; // 현재 상태: 좋아요 중 (1)
        }
    }
}