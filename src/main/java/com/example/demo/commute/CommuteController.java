package com.example.demo.commute;

import com.example.demo.dataroom.Dataroom;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String list(Model map, Principal principal, @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Commute> paging = this.cservice.getList(page - 1);
        map.addAttribute("paging", paging);
        MemberDto mdto = mservice.getMember(principal.getName());
        map.addAttribute("mdto", mdto);
        return "/commute/list";
    }

    //옵션으로 검색(전체 출퇴근 관리)
    @GetMapping("/search")
    public String getByOption(String type, Model map, String option, @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<CommuteDto> list2 = cservice.getByOption(type, option, page - 1);
        map.addAttribute("paging", list2);
        map.addAttribute("type", type);
        map.addAttribute("option", option);
        return "/commute/list";
    }

    //나의 출퇴근 기록
    @GetMapping("/mycommute")
    public String mycommute(Model map, @RequestParam(value = "page", defaultValue = "1") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member loginMember = (Member) authentication.getPrincipal();

        Page<Commute> paging = this.cservice.getMyList(loginMember.getId(), page - 1);
        map.addAttribute("paging", paging);
        return "/commute/mycommute";
    }

    //옵션으로 검색(내 출퇴근 이력)
    @GetMapping("/searchMyCommute")
    public String getByMycommute(String type, Model map, String option, @RequestParam(value = "page", defaultValue = "1") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member loginMember = (Member) authentication.getPrincipal();

        Page<CommuteDto> list = cservice.getByOptionAndMember(type, option, loginMember.getId(), page - 1);
        map.addAttribute("paging", list);
        map.addAttribute("type", type);
        map.addAttribute("option", option);
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
    public void edit(HttpServletResponse response, Model map, int commuteNum, String reason, String editStartTime, String editEndTime, String editBasicDate) {
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
            out.println(String.format("<script>alert('근태수정 신청이 완료됐습니다.'); location.href='/main';</script>"));
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //출퇴근기록 수정요청목록
    @GetMapping("/editRequestList")
    public String edit(Model map, @RequestParam(value = "page", defaultValue = "1") int page, String editStartTime, String editEndTime) {
        Page<Commute> paging = this.cservice.getEditRequestList(page - 1, editStartTime, editEndTime);
        map.addAttribute("paging", paging);
        return "/commute/editRequestList";
    }

    //수정요청 승인
    @RequestMapping("/approve")
    public String approve(int commuteNum) {
        CommuteDto cdto = cservice.get(commuteNum);
        cdto.setStartTime(cdto.getEditStartTime());
        cdto.setEndTime(cdto.getEditEndTime());
        cdto.setBasicDate(cdto.getEditBasicDate());
        cdto.setEditStartTime("");
        cdto.setEditEndTime("");
        cdto.setEditBasicDate("");
        cdto.setReason("");
        cservice.save(cdto);
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
    public String attendance(HttpServletResponse response, Principal principal, CommuteDto cdto, Model map) throws IOException {
        MemberDto mdto = mservice.getMember(principal.getName());
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime1 = LocalDateTime.now().format(formatter1);
        String formattedTime2 = LocalDateTime.now().format(formatter2);
        CommuteDto cdto2 = cservice.getByDateAndUserName(formattedTime1, principal.getName());
        System.out.println(cdto2);
        if (cdto2 == null) {
            mdto.setCstatus(1);
            mservice.save(mdto);
            cdto.setBasicDate(formattedTime1);
            cdto.setStartTime(formattedTime2);
            cdto.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getCstatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain(), mdto.getMonthMember()));
            cservice.save(cdto);
        } else {

            init(response);
            PrintWriter out = response.getWriter();
            out.write("<script>alert('" + "이미 출근처리가 완료되었습니다." + "');location.href='" + "/main" + "';</script>");
            out.flush();
        }
        return "redirect:/main";
    }

    //퇴근
    @PostMapping("/quit")
    public String quit(HttpServletResponse response, Principal principal, CommuteDto cdto) throws IOException {
        MemberDto mdto = mservice.getMember(principal.getName());
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime1 = LocalDateTime.now().format(formatter1);
        String formattedTime2 = LocalDateTime.now().format(formatter2);
        cdto = cservice.getByDateAndUserName(formattedTime1, principal.getName());
        System.out.println(cdto.getEndTime());
        System.out.println(formattedTime2);
        if (cdto.getEndTime() == null) {
            mdto.setCstatus(2);
            mservice.save(mdto);
            cdto.setEndTime(formattedTime2);
            System.out.println("test " + cdto.getCommuteNum());
            System.out.println("test " + cdto);
            cdto.setMember(new Member(mdto.getId(), mdto.getUsername(), mdto.getPwd(), mdto.getName(), mdto.getEmail(), mdto.getPhone(), mdto.getAddress(), mdto.getCompanyName(), mdto.getDeptCode(), mdto.getDeptName(), mdto.getCompanyRank(), mdto.getCompanyRankName(), mdto.getNewNo(), mdto.getComCall(), mdto.getIsMaster(), mdto.getStatus(), mdto.getCstatus(), mdto.getOriginFname(), mdto.getThumbnailFname(), mdto.getNewMemNo(), mdto.getRemain(), mdto.getMonthMember()));
            cservice.save(cdto);
        } else {

            init(response);
            PrintWriter out = response.getWriter();
            out.write("<script>alert('" + "이미 퇴근처리가 완료되었습니다." + "');location.href='" + "/main" + "';</script>");
            out.flush();
            //out.close();
        }
        return "redirect:/main";
    }
}