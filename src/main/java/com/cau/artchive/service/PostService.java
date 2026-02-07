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
        // TODO: 하드코딩된 "adminid" 제거.
        // SecurityContextHolder.getContext().getAuthentication().getName() 등을 활용하여 토큰에서 유저 ID 추출 필요.
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

    public PostResponseDto getPostDetail(Long postId) {

        // TODO: JWT 도입 시 SecurityContextHolder에서 현재 로그인 유저 정보를 가져옴
        User currentUser = userRepository.findByUserId("adminid")
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("POST_NOT_FOUND"));

        // 2. 게시글 DTO 변환
        return convertToDto(post);

//        // 3. 댓글 목록 조회 및 변환
//        List<CommentResponseDto> commentDtos = post.getComments().stream()
//                .map(c -> new CommentResponseDto(
//                        c.getCommentId(),
//                        c.getUser().getUserId(),
//                        c.getUser().getNickname(),
//                        c.getContent(),
//                        c.getCreatedAt()
//                ))
//                .collect(Collectors.toList());

//        return new PostDetailResponseDto(postDto, commentDtos);

    }

    @Transactional
    public Long updatePost(Long postId, PostRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("POST_NOT_FOUND"));

        // 더티 체킹을 통한 데이터 수정
        post.update(dto.getTitle(), dto.getContent(), dto.getCategory(),
                dto.getWorkName(), dto.getLocation(), dto.getRating(),
                dto.getViewingDate(), dto.isOpen());

        return post.getPostId();
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> searchPosts(String keyword) {
        return postRepository.findByWorkNameContainingIgnoreCaseOrTitleContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private PostResponseDto convertToDto(Post post) {

        // TODO 본인정보 받아오면 좋아요 여부 확인
//        boolean isLiked = likeRepository.existsByUserAndPost(currentUser, post);

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