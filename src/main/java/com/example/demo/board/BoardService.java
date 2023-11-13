package com.example.demo.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.employee.EmployeeDto;
import com.example.demo.member.Member;

import java.util.ArrayList;
import java.util.Date;
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

    // 전체검색. dao.findAll()
    public ArrayList<BoardDto> getAll() {
        List<Board> list = dao.findAll();
        ArrayList<BoardDto> list2 = new ArrayList<>();
        for (Board b : list) {
            list2.add(new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent()));
        }
        return list2;
    }
    
    // 옵션으로 검색
 	public ArrayList<BoardDto> getByOption(String type, String option) {
 		
 		ArrayList<BoardDto> list2 = new ArrayList<>();
 		if(type.equals("name")){
 			List<Board> list = dao.findByMemberNameLike("%" + option +"%");
 	        for (Board b : list) {
 	            list2.add(new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent()));
 	        }
 	        return list2;
 		}
 		if(type.equals("title")){
 			List<Board> list = dao.findByTitleLike("%" + option +"%");
 	        for (Board b : list) {
 	            list2.add(new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent()));
 	        }
 	       return list2;
 		}
 		else {
 			List<Board> list = dao.findAll();
 	        for (Board b : list) {
 	            list2.add(new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent()));
 	        }
 	        return list2;
 		}
 	}

    // 추가, 수정. dao.save()
    public BoardDto saveBoard(BoardDto b, int check) {
    	if(check==0) { //추가
	        Board b2 = dao.save(new Board(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent()));
	        return new BoardDto(b2.getNum(), b2.getWdate(), b2.getUdate(), b2.getMember(), b2.getTitle(), b2.getContent());
    	}
    	else if(check==1) {  //수정
    		Board b2 = dao.save(new Board(b.getNum(), b.getWdate(), new Date(), b.getMember(), b.getTitle(), b.getContent()));
            return new BoardDto(b2.getNum(), b2.getWdate(), b2.getUdate(), b2.getMember(), b2.getTitle(), b2.getContent());
    	}
    	else return null;
    }

    // pk 기준 삭제. dao.deleteById()
    public void delBoard(int num) {
        dao.deleteById(num);
    }

}
