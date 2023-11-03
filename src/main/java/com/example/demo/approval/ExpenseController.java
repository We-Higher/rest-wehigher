package com.example.demo.approval;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExpenseController {
    //지출결의서
    @RequestMapping("/expense")
    public String expense(){
        return "approval/expense";
    }
}
