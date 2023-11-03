package com.example.demo.approval;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VacationController {
    //휴가신청서
    @RequestMapping("/vacation")
    public String vaction(){
        return "approval/vacation";
    }
}
