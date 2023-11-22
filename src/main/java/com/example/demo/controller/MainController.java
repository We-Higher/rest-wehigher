package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {
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
        return "main";
    }
}
