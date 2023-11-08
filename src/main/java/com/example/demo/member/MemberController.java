package com.example.demo.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;

    @GetMapping("/login")
    public String loginForm() {
        return "member/login_form";
    }

    @PostMapping("/login")
    public String login(String username, String pwd, HttpSession session) {

        String path = "member/login_Form";
        MemberDto m = service.getMember(username);
        System.out.println("m = " + m);
        if (Objects.nonNull(m) && pwd.equals(m.getPwd())) {
            session.setAttribute("username", m);
            session.setAttribute("loginId", m.getName());
            System.out.println(m.getName());
            path = "redirect:/";
        }
        return path;
    }

    @GetMapping("/join")
    public String joinForm() {
        return "member/join_form";
    }

    @PostMapping("/join")
    public String join(MemberDto dto) {
        service.create(dto);
        return "redirect:/employee/list";
    }

    @GetMapping("/edit")
    public String editForm(String name, Model map) {
        MemberDto dto = service.getMemberByName(name);
        map.addAttribute("m", dto);
        return "member/edit_form";
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
}
