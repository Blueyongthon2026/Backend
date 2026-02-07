package com.cau.artchive.service;

import com.cau.artchive.dto.CommentRequestDto;
import com.cau.artchive.entity.Comment;
import com.cau.artchive.entity.Post;
import com.cau.artchive.entity.User;
import com.cau.artchive.repository.CommentRepository;
import com.cau.artchive.repository.PostRepository;
import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createComment(Long postId, CommentRequestDto dto) {
        User user = userRepository.findByUserId("adminid").orElseThrow(); // 임시 고정
        Post post = postRepository.findById(postId).orElseThrow();
        Comment comment = Comment.builder()
                .user(user).post(post).content(dto.getContent()).build();
        return commentRepository.save(comment).getCommentId();
    }

    public void updateComment(Long commentId, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.update(dto.getContent());
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}