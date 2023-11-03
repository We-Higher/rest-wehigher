package com.example.demo.approval;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReportController {
    //품의서
    @RequestMapping("/report")
    public String report(){
        return "approval/report";
    }
}
