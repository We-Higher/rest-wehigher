package com.example.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@PreAuthorize("isAuthenticated()")
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	@GetMapping("/list")
	public void list(ModelMap map) {
		ArrayList<EmployeeDto> list = service.getAll();
		map.addAttribute("list", list);
	}
	
	// 옵션으로 검색
	@PostMapping("/list")
	public String getbyfname(String type, Model map, String option) {
		System.out.println(type);
		System.out.println(option);
		ArrayList<EmployeeDto> list = service.getByOption(type, option);
		map.addAttribute("list", list);
		return "Employee/list";
	}
	
	/*
	//글작성
	@PostMapping("/add")
	public String add(EmployeeDto b) {
		service.saveEmployee(b);
		return "redirect:/Employee/list";
	}
	
	//글번호로검색(수정폼)
	@GetMapping("/edit")
	public void editForm(int num, ModelMap map) {
		EmployeeDto b = service.getEmployee(num);
		map.addAttribute("b", b);
	}
	
	//수정완료
	@PostMapping("/edit")
	public String edit(EmployeeDto b) {
		EmployeeDto b2 = service.getEmployee(b.getNum());
		b2.setTitle(b.getTitle());
		b2.setContent(b.getContent());
		service.saveEmployee(b2);
		return "redirect:/Employee/list";
	}
	
	//삭제
	@RequestMapping("/del")
	public String del(int num) {
		service.delEmployee(num);
		return "redirect:/Employee/list";
	}
	
	//작성자로 검색
	@RequestMapping("/getbywriter")
	public String getbywriter(String writer, Model map) {
		ArrayList<EmployeeDto> list = service.getByWriter(writer);
		map.addAttribute("list", list);
		return "Employee/list";
	}
	*/
}







