package com.example.demo.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.MemberDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	@Autowired
	private BoardDao dao;

	// pk로 검색. dao.findById()
	public BoardDto getBoard(int num) {
		Board b = dao.findById(num).orElse(null);
		return new BoardDto(b.getNum(), b.getId(), b.getUsername(), b.getTitle(), b.getContent(), b.getWdate(),
				b.getUdate());
	}

	// 전제검색. dao.findAll()
	public ArrayList<BoardDto> getAll() {
		List<Board> list = dao.findAll();
		ArrayList<BoardDto> list2 = new ArrayList<>();
		for (Board b : list) {
			list2.add(new BoardDto(b.getNum(), b.getId(), b.getUsername(), b.getTitle(), b.getContent(), b.getWdate(),
					b.getUdate()));
		}
		return list2;
	}

	// 추가, 수정. dao.save()
	public BoardDto saveBoard(BoardDto b) {
		Board b2 = dao.save(new BoardDto(b.getNum(), b.getId(), b.getUsername(), b.getTitle(), b.getContent(),
				b.getWdate(), b.getUdate()));
		return new BoardDto(b.getNum(), b.getId(), b.getUsername(), b.getTitle(), b.getContent(), b.getWdate(),
				b.getUdate());
	}

	// pk 기준 삭제. dao.deleteById()
	public void delBoard(int num) {
		dao.deleteById(num);
	}

}
