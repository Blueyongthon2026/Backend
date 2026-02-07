package com.cau.artchive.controller;

import com.cau.artchive.dto.CommentRequestDto;
import com.cau.artchive.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<Long> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(commentService.createComment(postId, dto));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto dto) {
        commentService.updateComment(commentId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
