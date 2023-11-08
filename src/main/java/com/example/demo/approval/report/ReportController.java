package com.example.demo.approval.report;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDao;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ReportController {
    @Autowired
    private ReportService rservice;

    //자바에서 script 사용하기
    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    }
    //품의서

    @GetMapping("/report")
    public ModelAndView report(HttpSession session) {
        ModelAndView mav = new ModelAndView("approval/report");
        MemberDto mdto = (MemberDto) session.getAttribute("username");
        mav.addObject("m", mdto);
        return mav;
    }

    @PostMapping("/report")
    public void addReport(HttpServletResponse response,ReportDto dto,HttpSession session){
        try {
            init(response);
            PrintWriter out = response.getWriter();
            MemberDto mdto = (MemberDto) session.getAttribute("username");
            dto.setMember(new Member(mdto.getId(),mdto.getUsername(),mdto.getPwd(),mdto.getName(),mdto.getEmail(),mdto.getPhone(),mdto.getAddress(),mdto.getCompanyName(),mdto.getDeptCode(),mdto.getCompanyRank(),mdto.getNewNo(),mdto.getComCall(),mdto.getIsMaster(),mdto.getStatus(),mdto.getOriginFname(),mdto.getThumbnailFname(),mdto.getNewMemNo(),mdto.getRemain()));
            rservice.saveReport(dto);
            out.println(String.format("<script>window.close();</script>"));
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
