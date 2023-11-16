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
    
    @Autowired
    private NotifyDao ndao;

    // pk로 검색. dao.findById()
    public BoardDto getBoard(int num) {
        Board b = dao.findById(num).orElse(null);
        return new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent());
    }
    
    // pk로 공지사항 검색. dao.findById()
    public NotifyDto getNotify(int num) {
    	Notify b = ndao.findById(num).orElse(null);
        return new NotifyDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent());
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
    
    // 공지사항 전체검색. ndao.findAll()
    public ArrayList<NotifyDto> getAllnotify() {
        List<Notify> list = ndao.findAll();
        ArrayList<NotifyDto> list2 = new ArrayList<>();
        for (Notify b : list) {
            list2.add(new NotifyDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent()));
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

    // 게시글 추가, 수정. dao.save()
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
    
    // 공지사항 추가, 수정. ndao.save()
    public NotifyDto saveNotify(NotifyDto b, int check) {
    	
    	if(check==0) { //추가
    		Notify b2 = ndao.save(new Notify(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent()));
	        return new NotifyDto(b2.getNum(), b2.getWdate(), b2.getUdate(), b2.getMember(), b2.getTitle(), b2.getContent());
    	}
    	else if(check==1) {  //수정
    		Notify b2 = ndao.save(new Notify(b.getNum(), b.getWdate(), new Date(), b.getMember(), b.getTitle(), b.getContent()));
            return new NotifyDto(b2.getNum(), b2.getWdate(), b2.getUdate(), b2.getMember(), b2.getTitle(), b2.getContent());
    	}
    	else return null;
    }

    // 게시글 pk 기준 삭제. dao.deleteById()
    public void delBoard(int num) {
        dao.deleteById(num);
    }
    
    // 공지사항 pk 기준 삭제. dao.deleteById()
    public void delNotify(int num) {
        ndao.deleteById(num);
    }

}
