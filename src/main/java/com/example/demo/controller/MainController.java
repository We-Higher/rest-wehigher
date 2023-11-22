package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {
    @RequestMapping("/")
    public String Home(HttpSession session) {
        return "member/login_form";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/main")
    public String Main(Model map) {
        return "main";
    }
}
