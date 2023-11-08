package com.example.demo.approval.vacation;

import com.example.demo.member.Member;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Controller
public class VacationController {
    @Autowired
    private VacationService vservice;
    @Autowired
    private MemberService mservice;

    //자바에서 script 사용하기
    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    }

    //휴가신청서
    @GetMapping("/vacation")
    public ModelAndView vacation(HttpSession session) {
        ModelAndView mav = new ModelAndView("approval/vacation");
        MemberDto mdto = (MemberDto) session.getAttribute("username");
        mav.addObject("m", mdto);
        return mav;
    }

    @PostMapping("/vacation")
    public void addVacation(HttpServletResponse response, VacationDto dto, HttpSession session) {
        try {
            init(response);
            PrintWriter out = response.getWriter();
            MemberDto mdto = (MemberDto) session.getAttribute("username");
            String startDate = dto.getStartDate();
            String endDate = dto.getEndDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDateLocalDate = LocalDate.parse(startDate, formatter);
            LocalDate endDateLocalDate = LocalDate.parse(endDate, formatter);
            long daysBetween = ChronoUnit.DAYS.between(startDateLocalDate, endDateLocalDate);
            int remain = (int) daysBetween;
            System.out.println("remain = " + remain);
            mdto.setRemain(mdto.getRemain() - remain);
            mservice.save(mdto);
            dto.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getCompanyRank(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain()));
            vservice.saveVacation(dto);
            out.println(String.format("<script>window.close();</script>"));
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
