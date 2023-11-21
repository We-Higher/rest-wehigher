package com.example.demo.board;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.employee.EmployeeDto;
import com.example.demo.member.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt());
    }
    
    // pk로 공지사항 검색. dao.findById()
    public NotifyDto getNotify(int num) {
    	Notify b = ndao.findById(num).orElse(null);
        return new NotifyDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt());
    }

    // 전체검색. dao.findAll()
    public ArrayList<BoardDto> getAll() {
        List<Board> list = dao.findAll();
        ArrayList<BoardDto> list2 = new ArrayList<>();
        for (Board b : list) {
            list2.add(new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
        }
        return list2;
    }
    
    // 공지사항 전체검색. ndao.findAll()
    public ArrayList<NotifyDto> getAllnotify() {
        List<Notify> list = ndao.findAll();
        ArrayList<NotifyDto> list2 = new ArrayList<>();
        for (Notify b : list) {
            list2.add(new NotifyDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
        }
        return list2;
    }
    
    // 옵션으로 검색
 	public ArrayList<BoardDto> getByOption(String type, String option) {
 		
 		ArrayList<BoardDto> list2 = new ArrayList<>();
 		if(type.equals("name")){
 			List<Board> list = dao.findByMemberNameLike("%" + option +"%");
 	        for (Board b : list) {
 	            list2.add(new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
 	        }
 	        return list2;
 		}
 		if(type.equals("title")){
 			List<Board> list = dao.findByTitleLike("%" + option +"%");
 	        for (Board b : list) {
 	            list2.add(new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
 	        }
 	       return list2;
 		}
 		else {
 			List<Board> list = dao.findAll();
 	        for (Board b : list) {
 	            list2.add(new BoardDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
 	        }
 	        return list2;
 		}
 	}
 	
    // 공지사항 옵션으로 검색
 	public ArrayList<NotifyDto> getByOption2(String type, String option) {
 		
 		ArrayList<NotifyDto> list2 = new ArrayList<>();
 		if(type.equals("name")){
 			List<Notify> list = ndao.findByMemberNameLike("%" + option +"%");
 	        for (Notify b : list) {
 	            list2.add(new NotifyDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
 	        }
 	        return list2;
 		}
 		if(type.equals("title")){
 			List<Notify> list = ndao.findByTitleLike("%" + option +"%");
 	        for (Notify b : list) {
 	            list2.add(new NotifyDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
 	        }
 	       return list2;
 		}
 		else {
 			List<Notify> list = ndao.findAll();
 	        for (Notify b : list) {
 	            list2.add(new NotifyDto(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
 	        }
 	        return list2;
 		}
 	}

    // 게시글 추가, 수정. dao.save()
    public BoardDto saveBoard(BoardDto b, int check) {
    	
    	if(check==0) { //추가
    		
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedTime1 = LocalDateTime.now().format(formatter1);
            b.setUdate(formattedTime1);
            b.setWdate(formattedTime1);
	        Board b2 = dao.save(new Board(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
	        return new BoardDto(b2.getNum(), b2.getWdate(), b2.getUdate(), b2.getMember(), b2.getTitle(), b2.getContent(), b2.getCnt());
    	}
    	else if(check==1) {  //수정
    		
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedTime1 = LocalDateTime.now().format(formatter1);
            b.setUdate(formattedTime1);
    		Board b2 = dao.save(new Board(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
            return new BoardDto(b2.getNum(), b2.getWdate(), b2.getUdate(), b2.getMember(), b2.getTitle(), b2.getContent(), b2.getCnt());
    	}
    	else return null;
    }
    
    // 공지사항 추가, 수정. ndao.save()
    public NotifyDto saveNotify(NotifyDto b, int check) {
    	
    	if(check==0) { //추가
    		
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedTime1 = LocalDateTime.now().format(formatter1);
            b.setUdate(formattedTime1);
            b.setWdate(formattedTime1);
    		Notify b2 = ndao.save(new Notify(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
	        return new NotifyDto(b2.getNum(), b2.getWdate(), b2.getUdate(), b2.getMember(), b2.getTitle(), b2.getContent(), b.getCnt());
    	}
    	else if(check==1) {  //수정
    		
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedTime1 = LocalDateTime.now().format(formatter1);
            b.setWdate(formattedTime1);
    		Notify b2 = ndao.save(new Notify(b.getNum(), b.getWdate(), b.getUdate(), b.getMember(), b.getTitle(), b.getContent(), b.getCnt()));
            return new NotifyDto(b2.getNum(), b2.getWdate(), b2.getUdate(), b2.getMember(), b2.getTitle(), b2.getContent(), b.getCnt());
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
    
    // 조회수 증가
    public void editCnt(int num) {
        dao.updateCnt(num);
    }
    
    // 공지사항 조회수 증가
    public void editCnt2(int num) {
        ndao.updateCnt(num);
    }

}
