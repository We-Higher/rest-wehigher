package com.example.demo.controller;

import com.example.demo.board.BoardService;
import com.example.demo.board.Notify;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping("")
public class MainController {
    @Autowired
    BoardService bservice;
    @Autowired
    MemberService mservice;


    @RequestMapping("/")
    public String Home(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            return "redirect:/main";
        } else {
            return "redirect:/member/login";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/main")
    public String Main(Model map) {
        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "num")); // id는 Notify 엔티티의 속성입니다. 적절하게 변경해야 합니다.
        Page<Notify> pagingNotify = this.bservice.getNotifyMain(pageable);
        map.addAttribute("pagingNotify", pagingNotify);
        ArrayList<MemberDto> list = mservice.getAll();
        map.addAttribute("list", list);
        ArrayList<MemberDto> monthMemlist = new ArrayList<>();
        for (MemberDto dto : list) {
            if (dto.getMonthMember() == 1) {
                monthMemlist.add(new MemberDto(dto.getId(), dto.getUsername(), dto.getPwd(), dto.getName(), dto.getEmail(), dto.getPhone(), dto.getAddress(), dto.getCompanyName(), dto.getDeptCode(), dto.getDeptName(), dto.getCompanyRank(), dto.getCompanyRankName(), dto.getNewNo(), dto.getComCall(), dto.getIsMaster(), dto.getStatus(), dto.getCstatus(), dto.getOriginFname(), dto.getThumbnailFname(), dto.getNewMemNo(), dto.getRemain(), dto.getMonthMember(), null));
            }
        }
        map.addAttribute("monthMemlist", monthMemlist);
        return "main";
    }

}