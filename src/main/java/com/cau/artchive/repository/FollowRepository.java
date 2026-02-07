package com.cau.artchive.repository;

import com.cau.artchive.entity.Follow;
import com.cau.artchive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 내가 팔로우하는 사람(toUser)들의 정보를 한 번에 가져오기
    @Query("select f from Follow f join fetch f.toUser where f.fromUser.dbid = :fromDbid")
    List<Follow> findAllByFromUserWithToUser(@Param("fromDbid") Long fromDbid);

    // 나를 팔로우하는 사람(fromUser)들의 정보를 한 번에 가져오기
    @Query("select f from Follow f join fetch f.fromUser where f.toUser.dbid = :toDbid")
    List<Follow> findAllByToUserWithFromUser(@Param("toDbid") Long toDbid);

    // 팔로우 취소 시 사용
    void deleteByFromUserAndToUser(User fromUser, User toUser);

    // 이미 팔로우 중인지 확인
    boolean existsByFromUserAndToUser(User fromUser, User toUser);
}