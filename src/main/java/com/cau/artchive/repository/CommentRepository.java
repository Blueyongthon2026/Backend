package com.cau.artchive.repository;

import com.cau.artchive.entity.Comment;
import com.cau.artchive.entity.Like;
import com.cau.artchive.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.user where c.post.postId = :postId")
    List<Comment> findAllByPostIdWithUser(@Param("postId") Long postId);

    List<Comment> findAllByPostOrderByCreatedAtAsc(Post post);
}