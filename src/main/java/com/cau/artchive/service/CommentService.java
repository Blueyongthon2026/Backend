package com.cau.artchive.service;

import com.cau.artchive.dto.CommentRequestDto;
import com.cau.artchive.dto.CommentResponseDto;
import com.cau.artchive.entity.Comment;
import com.cau.artchive.entity.Post;
import com.cau.artchive.entity.User;
import com.cau.artchive.repository.CommentRepository;
import com.cau.artchive.repository.PostRepository;
import com.cau.artchive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    @Transactional
    public Long updateComment(Long commentId, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("COMMENT_NOT_FOUND"));

        // TODO: JWT 도입 시 '현재 로그인 유저 == 댓글 작성자'인지 확인하는 권한 로직 추가 필요

        comment.update(dto.getContent());

        return comment.getCommentId();
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        // 작성하신 리포지토리 메서드 활용: findAllByPostIdWithUser
        return commentRepository.findAllByPostIdWithUser(postId).stream()
                .map(c -> new CommentResponseDto(
                        c.getCommentId(),
                        c.getUser().getUserId(),
                        c.getUser().getNickname(),
                        c.getContent(),
                        c.getCreatedAt()
                ))
                .toList();
    }
}