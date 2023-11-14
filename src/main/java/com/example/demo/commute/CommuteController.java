package com.example.demo.commute;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/commute")
public class CommuteController {
    @Autowired
    private CommuteService cservice;
    @Autowired
    private MemberService mservice;

    //자바에서 script 사용하기
    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    }

    //근태관리
    @GetMapping("/list")
    public String list(Model map, Principal principal) {
        ArrayList<CommuteDto> list = cservice.getAll();
        MemberDto mdto = mservice.getMember(principal.getName());
        map.addAttribute("mdto", mdto);
        map.addAttribute("list", list);
        return "/commute/list";
    }

    //옵션으로 검색(전체 출퇴근 관리)
    @PostMapping("/list")
    public String getByOption(String type, Model map, String option1, String option2) {
        if (!option1.isEmpty()) {
            ArrayList<CommuteDto> list1 = cservice.getByOption(type, option1);
            map.addAttribute("list", list1);
        } else if (!option2.isEmpty()) {
            ArrayList<CommuteDto> list2 = cservice.getByOption(type, option2);
            map.addAttribute("list", list2);
        }
        return "/commute/list";
    }

    //나의 출퇴근 기록
    @GetMapping("/mycommute")
    public String mycommute(Model map, Principal principal) {
        MemberDto mdto = mservice.getMember(principal.getName());
        ArrayList<CommuteDto> list = cservice.getByMemberId(mdto.getId());
        map.addAttribute("list", list);
        return "/commute/mycommute";
    }

    //옵션으로 검색(내 출퇴근 이력)
    @PostMapping("/mycommute")
    public String getByMycommut(String type, Model map, String option, Principal principal) {
        ArrayList<CommuteDto> list2 = new ArrayList<>();
        MemberDto mdto = mservice.getMember(principal.getName());
        ArrayList<CommuteDto> list = cservice.getByOption(type, option);
        for (CommuteDto cdto : list) {
            if (mdto.getId() == cdto.getMember().getId()) {
                list2.add(new CommuteDto(cdto.getCommuteNum(), cdto.getMember(), cdto.getBasicDate(), cdto.getStartTime(), cdto.getEndTime(), cdto.getReason(), cdto.getEditStartTime(), cdto.getEditEndTime(), cdto.getEditBasicDate()));
            }
        }
        map.addAttribute("list", list2);
        return "/commute/mycommute";
    }

    //출퇴근기록 수정요청폼
    @GetMapping("/editRequest")
    public String editRequest(Model map, int commuteNum) {
        CommuteDto cdto = cservice.get(commuteNum);
        map.addAttribute("cdto", cdto);
        return "/commute/editRequestForm";
    }

    //출퇴근기록 수정요청 저장
    @PostMapping("/editRequest")
    public String edit(HttpServletResponse response, Model map, int commuteNum, String reason, String editStartTime, String editEndTime, String editBasicDate) {
        try {
            CommuteDto cdto = cservice.get(commuteNum);
            cdto.setEditBasicDate(editBasicDate);
            cdto.setReason(reason);
            cdto.setEditStartTime(editStartTime);
            cdto.setEditEndTime(editEndTime);
            cservice.save(cdto);
            ArrayList<CommuteDto> list = new ArrayList<>();
            list.add(cdto);
            map.addAttribute("list", list);
            init(response);
            PrintWriter out = response.getWriter();
            out.println(String.format("<script>window.close();</script>"));
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "/commute/editRequestList";
    }

    //출퇴근기록 수정요청목록
    @GetMapping("/editRequestList")
    public String edit(Model map) {
        ArrayList<CommuteDto> list = cservice.getAll();
        ArrayList<CommuteDto> list2 = new ArrayList<>();
        for (CommuteDto cdto : list) {
            if (cdto.getEditStartTime() != null || cdto.getEditEndTime() != null) {
                list2.add(new CommuteDto(cdto.getCommuteNum(), cdto.getMember(), cdto.getBasicDate(), cdto.getStartTime(), cdto.getEndTime(), cdto.getReason(), cdto.getEditStartTime(), cdto.getEditEndTime(), cdto.getEditBasicDate()));
            }
        }
        map.addAttribute("list", list2);
        return "/commute/editRequestList";
    }

    //수정요청 승인
    @RequestMapping("/approve")
    public String approve(int commuteNum) {
        CommuteDto cdto = cservice.get(commuteNum);
        if (cdto.getEditStartTime() != null && cdto.getEditBasicDate() != null) {
            cdto.setStartTime(cdto.getEditStartTime());
            cdto.setBasicDate(cdto.getEditBasicDate());
            cdto.setEditBasicDate("");
            cdto.setEditStartTime("");
            cdto.setReason("");
            cservice.save(cdto);
        } else if (cdto.getEditEndTime() != null && cdto.getEditBasicDate() != null) {
            cdto.setEndTime(cdto.getEditEndTime());
            cdto.setBasicDate(cdto.getEditBasicDate());
            cdto.setEditBasicDate("");
            cdto.setEditEndTime("");
            cdto.setReason("");
            cservice.save(cdto);
        }
        return "redirect:/commute/editRequestList";
    }

    //수정요청 거절
    @RequestMapping("/cancel")
    public String cancel(int commuteNum) {
        CommuteDto cdto = cservice.get(commuteNum);
        cdto.setEditBasicDate("");
        cdto.setEditStartTime("");
        cdto.setEditEndTime("");
        cdto.setReason("");
        cservice.save(cdto);
        return "redirect:/commute/editRequestList";
    }

    //출근
    @PostMapping("/attendance")
    public String attendance(Principal principal, CommuteDto cdto, Model map) {
        MemberDto mdto = mservice.getMember(principal.getName());
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime1 = LocalDateTime.now().format(formatter1);
        String formattedTime2 = LocalDateTime.now().format(formatter2);
        mdto.setStatus(1);
        mservice.save(mdto);
        cdto.setBasicDate(formattedTime1);
        cdto.setStartTime(formattedTime2);
        cdto.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain()));
        cservice.save(cdto);
        return "redirect:/commute/list";
    }

    //퇴근
    @PostMapping("/quit")
    public String quit(Principal principal, CommuteDto cdto) {
        MemberDto mdto = mservice.getMember(principal.getName());
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime1 = LocalDateTime.now().format(formatter1);
        String formattedTime2 = LocalDateTime.now().format(formatter2);
        mdto.setStatus(0);
        mservice.save(mdto);
        cdto.setBasicDate(formattedTime1);
        cdto.setEndTime(formattedTime2);
        cdto.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain()));
        cservice.save(cdto);
        return "redirect:/commute/list";
    }
}