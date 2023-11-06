package com.example.demo.board;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.board.BoardDto;

@Repository
public interface BoardDao extends JpaRepository<Board, Integer> {
	
	Board save(BoardDto boardDto);
}

