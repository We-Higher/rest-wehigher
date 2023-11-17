package com.example.demo.board;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.member.Member;

@Repository
public interface BoardDao extends JpaRepository<Board, Integer> {
	
    @Transactional
    @Modifying
    @Query(value="update board set cnt=cnt+1 where num=:num", nativeQuery=true)
    void updateCnt(@Param("num") int num);
	ArrayList<Board> findByMemberNameLike(String name);  //이름으로 검색
	ArrayList<Board> findByTitleLike(String name);  //제목으로 검색
}

