package com.example.demo.schedule;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {
	
    private final ScheduleDao dao;
    
    //자바에서 script 사용하기
    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    }

    //추가,수정
    public ScheduleDto save(ScheduleDto dto) {
        Schedule entity = dao.save(new Schedule(dto.getId(), dto.getTitle(), dto.getStartDate(), dto.getEndDate(), dto.getCnt(), dto.getClassName()));
        return new ScheduleDto(entity.getId(), entity.getTitle(), entity.getStartDate(), entity.getEndDate(), entity.getCnt(), entity.getClassName());
    }

    public ScheduleDto get(int id) {
        Schedule entity = dao.findById(id).orElse(null);
        return new ScheduleDto(entity.getId(), entity.getTitle(), entity.getStartDate(), entity.getEndDate(), entity.getCnt(),entity.getClassName());
    }
    public ArrayList<ScheduleDto> getAll(){
        List<Schedule> s = dao.findAll();
        ArrayList<ScheduleDto> list = new ArrayList<>();
        for(Schedule entity:s) {
            list.add(new ScheduleDto(entity.getId(), entity.getTitle(), entity.getStartDate(), entity.getEndDate(), entity.getCnt(), entity.getClassName()));
        }
        return list;
    }

    //삭제
    public void del(int id) {
    	
    	Schedule entity = dao.findById(id).orElse(null);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Member loginMember = (Member) authentication.getPrincipal();
    	if(entity.getCnt()==0) {
    		dao.deleteById(id);
    	}
    	else {
    		
    		if(loginMember.getIsMaster()==0) {
    			
                /*PrintWriter out = response.getWriter();
                out.write("<script>alert('"+"관리자만 삭제 가능합니다."+"');location.href='"+"/schedule"+"';</script>");
                out.flush();*/
    			System.out.println("관리자만 삭제 가능합니다.");
    		}
    		else {
    			
    			dao.deleteById(id);
    		}
    		
    	}
    }
}
