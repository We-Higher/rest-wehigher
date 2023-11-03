package com.example.demo.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;

    @GetMapping("/login")
    public String loginForm() {
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(String username, String pwd, HttpSession session) {
        String path = "member/login";
        MemberDto m = service.getMember(username);
        if (Objects.nonNull(m) && pwd.equals(m.getPwd())) {
            session.setAttribute("user", m);
            path = "/";
        }
        return path;
    }
}
