package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {
    @RequestMapping("/")
    public String Home(HttpSession session) {
        return "redirect:/main";
    }

    @RequestMapping("/main")
    public String Main() {
        return "main";
    }
}
