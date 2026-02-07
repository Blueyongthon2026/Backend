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

    public Long createComment(User user, Long postId, CommentRequestDto dto) {
        Post post = postRepository.findById(postId).orElseThrow();
        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(dto.getContent())
                .build();
        return commentRepository.save(comment).getCommentId();
    }

    public Long updateComment(User user, Long commentId, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().getDbid().equals(user.getDbid())) {
            throw new RuntimeException("FORBIDDEN");
        }
        comment.update(dto.getContent());
        return comment.getCommentId();
    }

    public void deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().getDbid().equals(user.getDbid())) {
            throw new RuntimeException("FORBIDDEN");
        }
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPost(Long postId) {
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
