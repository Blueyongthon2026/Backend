package com.cau.artchive.repository;

import com.cau.artchive.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p join fetch p.user order by p.createdAt desc")
    List<Post> findAllWithUser();
}
