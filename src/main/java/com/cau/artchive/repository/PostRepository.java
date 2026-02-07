package com.cau.artchive.repository;

import com.cau.artchive.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p join fetch p.user order by p.createdAt desc")
    List<Post> findAllWithUser();

    // 작품명 또는 제목에 키워드가 포함된 게시글 찾기 (대소문자 무시)
    List<Post> findByWorkNameContainingIgnoreCaseOrTitleContainingIgnoreCase(String workKeyword, String titleKeyword);


}