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

    public boolean toggleLike(User user, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        if (likeRepository.existsByUserAndPost(user, post)) {
            likeRepository.deleteByUserAndPost(user, post);
            post.updateLikeCount(false);
            return false;
        } else {
            likeRepository.save(Like.builder()
                    .user(user)
                    .post(post)
                    .build());
            post.updateLikeCount(true);
            return true;
        }
    }
}
