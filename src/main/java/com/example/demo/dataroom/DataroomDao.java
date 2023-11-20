package com.example.demo.dataroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DataroomDao extends JpaRepository<Dataroom,Integer> {
    @Transactional
    @Modifying
    @Query(value="update dataroom set cnt=cnt+1 where num=:num", nativeQuery=true)
    void updateCnt(@Param("num") int num);
    List<Dataroom> findByMemberNameContains(String writer);//작성자로 검색
    List<Dataroom> findByTitleContains(String title);//제목으로 검색
}