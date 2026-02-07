package com.cau.artchive.service;

import com.cau.artchive.dto.CommentResponseDto;
import com.cau.artchive.dto.PostDetailResponseDto;
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
        User adminUser = userRepository.findByUserId("adminid") //TODO jwt
                .orElseThrow(() -> new IllegalStateException("관리자 계정이 없습니다."));

        Post post = Post.builder()
                .user(adminUser)
                .coverImage("https://ibb.co/B512v7xL") //TODO Jwt
                .category(dto.getCategory())
                .workName(dto.getWorkName())
                .title(dto.getTitle())
                .content(dto.getContent())
                .location(dto.getLocation())
                .rating(dto.getRating())
                .viewingDate(dto.getViewingDate())
                .isPublic(dto.isOpen()) // 추가
                .build();

        return postRepository.save(post).getPostId();
    }

    public List<PostResponseDto> getAllPosts() {
        // N+1 방지를 위해 fetch join된 쿼리 사용
        return postRepository.findAllWithUser().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PostDetailResponseDto getPostDetail(Long postId) {
        // 1. 게시글 조회 (N+1 방지를 위해 fetch join 처리된 쿼리 권장)
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 2. 게시글 DTO 변환
        PostResponseDto postDto = convertToDto(post);

        // 3. 댓글 목록 조회 및 변환 (batch_fetch_size 설정 덕분에 쿼리 효율적)
        List<CommentResponseDto> commentDtos = post.getComments().stream()
                .map(c -> new CommentResponseDto(
                        c.getCommentId(),
                        c.getUser().getUserId(),
                        c.getUser().getNickname(),
                        c.getContent(),
                        c.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new PostDetailResponseDto(postDto, commentDtos);
    }

    private PostResponseDto convertToDto(Post post) {
        return PostResponseDto.builder()
                .postId(post.getPostId())
                .authorUserId(post.getUser().getUserId())
                .authorNickname(post.getUser().getNickname())
                .coverImage(post.getCoverImage())
                .category(post.getCategory())
                .workName(post.getWorkName())
                .title(post.getTitle())
                .content(post.getContent())
                .location(post.getLocation())
                .rating(post.getRating())
                .viewingDate(post.getViewingDate())
                .likeCount(post.getLikeCount())
                .open(post.isOpen())
                .createdAt(post.getCreatedAt())
                .build();
    }
}