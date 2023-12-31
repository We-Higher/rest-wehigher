package com.example.demo.employee;

import com.example.demo.commute.Commute;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;
import com.example.demo.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.util.ArrayList;

@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;
    @Autowired
    private MemberService mservice;

    // 임직원 목록
    @GetMapping("/list")
    public String list(Model map, @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Member> paging = this.mservice.getList(page - 1);
        map.addAttribute("paging", paging);
        ArrayList<MemberDto> list = mservice.getAll();
        map.addAttribute("list", list);
        return "employee/list";
    }

    // 옵션으로 검색
    @GetMapping("/search")
    public String getbyOption(String type, Model map, String option, @RequestParam(value = "page", defaultValue = "1") int page) {
        System.out.println(type);
        System.out.println(option);
        Page<MemberDto> paging = service.getByOption(type, option, page-1);
        map.addAttribute("paging", paging);
        map.addAttribute("type", type);
        map.addAttribute("option", option);
        return "employee/list";
    }
}







