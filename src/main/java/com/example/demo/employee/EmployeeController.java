package com.example.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.ArrayList;

@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	// 임직원 목록
	@GetMapping("/list")
	public void list(ModelMap map) {
		ArrayList<EmployeeDto> list = service.getAll();
		map.addAttribute("list", list);
	}
	
	// 옵션으로 검색
	@PostMapping("/list")
	public String getbyOption(String type, Model map, String option) {
		System.out.println(type);
		System.out.println(option);
		ArrayList<EmployeeDto> list = service.getByOption(type, option);
		map.addAttribute("list", list);
		return "Employee/list";
	}
}







