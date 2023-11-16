package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.commute.CommuteDto;
import com.example.demo.commute.CommuteService;

@Controller
@RequestMapping("")
@PreAuthorize("isAuthenticated()")
public class MainController {
	
    @Autowired
    private CommuteService cservice;
    
    @RequestMapping("/")
    public String Home(HttpSession session) {
        return "redirect:/main";
    }

    @RequestMapping("/main")
    public String Main(Model map) {
        return "main";
    }
}
