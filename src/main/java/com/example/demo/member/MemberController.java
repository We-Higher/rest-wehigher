package com.example.demo.member;

import com.example.demo.member.dto.MemberJoinDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@PreAuthorize("hasRole(\"ADMIN\")")
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService service;
    private final PasswordEncoder passwordEncoder;
    
    //자바에서 script 사용하기
    public static void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String loginForm() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            return "redirect:/main";
        } else {
            return "member/login_form";
        }
    }
    
    @GetMapping("/join")
    public String joinForm(MemberDto dto) {
        return "member/join";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/join")
    public String join(MemberJoinDto memberJoinDto) {
        System.out.println("memberJoinDto = " + memberJoinDto);
        log.info("memberJoinDto : {}", memberJoinDto);
        service.create(memberJoinDto);
        return "redirect:/employee/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String editForm( String name, Model map) {
        MemberDto dto = service.getMemberByName(name);
        map.addAttribute("m", dto);
        return "member/edit";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit")
    public String edit(MemberDto dto) {
        System.out.println("dto.getDeptCode() = " + dto.getDeptCode());
        System.out.println("dto.getDeptName() = " + dto.getDeptName());
        MemberDto m = service.getMemberByName(dto.getName());
        m.setUsername(dto.getUsername());
        m.setPwd(passwordEncoder.encode(dto.getPwd()));
        m.setName(dto.getName());
        m.setCompanyName(dto.getCompanyName());
        m.setDeptCode(dto.getDeptCode());
        m.setCompanyRank(dto.getCompanyRank());
        m.setNewNo(dto.getNewNo());
        m.setEmail(dto.getEmail());
        m.setAddress(dto.getAddress());
        m.setComCall(dto.getComCall());
        m.setPhone(dto.getPhone());
        m.setIsMaster(dto.getIsMaster());
        m.setStatus(dto.getStatus());
        service.save(m);
        return "redirect:/employee/list";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/del")
    public String delete(Long id) {
        service.delete(id);
        return "redirect:/employee/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String mypageForm(String name, Model map) {
        MemberDto dto = service.getMember(name);
        map.addAttribute("m", dto);
        return "member/mypage_new";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/pwdedit")
    public String pwdedit(HttpServletResponse response, MemberDto dto) throws IOException {
        MemberDto m = service.getMemberByName(dto.getName());
        m.setUsername(dto.getUsername());
        m.setPwd(passwordEncoder.encode(dto.getPwd()));
        m.setName(dto.getName());
        m.setCompanyName(dto.getCompanyName());
        m.setDeptCode(dto.getDeptCode());
        m.setCompanyRank(dto.getCompanyRank());
        m.setNewNo(dto.getNewNo());
        m.setEmail(dto.getEmail());
        m.setAddress(dto.getAddress());
        m.setComCall(dto.getComCall());
        m.setPhone(dto.getPhone());
        m.setIsMaster(dto.getIsMaster());
        m.setStatus(dto.getStatus());
        service.save(m);
        init(response);
        PrintWriter out = response.getWriter();
        out.write("<script>alert('"+"비밀번호가 변경되었습니다. 다시 로그인해주세요."+"');location.href='"+"/member/logout"+"';</script>");
        out.flush();
        return "redirect:/member/logout";
    }

    @PostMapping("/nameLike")
    public String nameLike(String name, Model map){
        ArrayList<MemberDto> list = service.getByNameLike(name);
        map.addAttribute("list",list);
        return "main";
    }
}