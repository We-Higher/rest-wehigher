package com.example.demo.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    @Autowired
    private BoardDao dao;

    // pk로 검색. dao.findById()
    public BoardDto getBoard(int num) {
        Board b = dao.findById(num).orElse(null);
        return new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent());
    }

    // 전제검색. dao.findAll()
    public ArrayList<BoardDto> getAll() {
        List<Board> list = dao.findAll();
        ArrayList<BoardDto> list2 = new ArrayList<>();
        for (Board b : list) {
            list2.add(new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent()));
        }
        return list2;
    }

    // 추가, 수정. dao.save()
    public BoardDto saveBoard(BoardDto b) {
        Board b2 = dao.save(new Board(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent()));
        return new BoardDto(b2.getNum(), b2.getWdate(), b2.getUdate(), b2.getMember(), b2.getTitle(), b2.getContent());
    }

    // pk 기준 삭제. dao.deleteById()
    public void delBoard(int num) {
        dao.deleteById(num);
    }

}
