package com.example.demo.member;

import com.example.demo.member.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasRole(\"ADMIN\")")
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String loginForm() {
        return "member/login_form";
    }
    
    @GetMapping("/join")
    public String joinForm() {
        return "member/join";
    }

    @PostMapping("/join")
    public String join(MemberJoinDto memberJoinDto) {
        service.create(memberJoinDto);
        return "redirect:/employee/list";
    }

    @GetMapping("/edit")
    public String editForm(String name, Model map) {
        MemberDto dto = service.getMemberByName(name);
        map.addAttribute("m", dto);
        return "member/edit";
    }

    @PostMapping("/edit")
    public String edit(MemberDto dto) {
        MemberDto m = service.getMemberByName(dto.getName());
        
        m.setUsername(dto.getUsername());
        m.setPwd(dto.getPwd());
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

    @RequestMapping("/del")
    public String delete(Long id){
        service.delete(id);
        return "redirect:/employee/list";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String mypageForm(String name, Model map) {
        MemberDto dto = service.getMember(name);
        map.addAttribute("m", dto);
        return "member/mypage";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/pwdedit")
    public String pwdedit(MemberDto dto) {
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
        return "redirect:/main";
    }
}
