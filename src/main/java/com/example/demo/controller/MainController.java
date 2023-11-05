package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {
    @RequestMapping("/")
    public String Home(HttpSession session) {
        // 비로그인 상태면 redirect /member/login
        if (session.getAttribute("username") == null) {
            return "redirect:/member/login";
        }
        // 로그인 상태면 redirect main
        else {
            return "redirect:/main";
        }
    }

    @RequestMapping("/main")
    public String Main() {
        return "main";
    }
}
