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
public interface NotifyDao extends JpaRepository<Notify, Integer> {
	
    @Transactional
    @Modifying
    @Query(value="update notify set cnt=cnt+1 where num=:num", nativeQuery=true)
    void updateCnt(@Param("num") int num);
	ArrayList<Notify> findByMemberNameLike(String name);  //이름으로 검색
	ArrayList<Notify> findByTitleLike(String name);  //제목으로 검색
}

