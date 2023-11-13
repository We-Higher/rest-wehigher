package com.example.demo.board;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;

@Repository
public interface BoardDao extends JpaRepository<Board, Integer> {
	
	ArrayList<Board> findByMemberNameLike(String name);  //이름으로 검색
	ArrayList<Board> findByTitleLike(String name);  //제목으로 검색
}

