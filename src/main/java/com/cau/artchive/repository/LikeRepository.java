package com.cau.artchive.repository;

import com.cau.artchive.entity.Like;
import com.cau.artchive.entity.Post;
import com.cau.artchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);
    void deleteByUserAndPost(User user, Post post);
}
