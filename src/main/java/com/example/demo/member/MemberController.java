package com.example.demo.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        String path = "login_form";
        MemberDto m = service.getMember(username);
        System.out.println("m = " + m);
        if (Objects.nonNull(m) && pwd.equals(m.getPwd())) {
            session.setAttribute("username", m);
            path = "redirect:/"; // 로그인 성공시 이동할 페이지
        }
        return path;
    }
}
