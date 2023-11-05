package com.example.demo.employee;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	//@Autowired
	//private EmployeeService service;
	
	@GetMapping("/list")
	public String emplist() {
		
		return "employee/employee_list";
	}
	
	//글목록
	/*
	@RequestMapping("/list")
	public void list(ModelMap map) {//map은 자동으로 뷰페이지로 전달됨
		ArrayList<EmployeeDto> list = service.getAll();
		map.addAttribute("list", list);
		//뷰 페이지 경로: /Employee/list.jsp
	}
	
	@GetMapping("/add")
	public void addForm() {}
	
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
	
	//제목으로 검색
	@RequestMapping("/getbytitle")
	public String getbytitle(String title, Model map) {
		ArrayList<EmployeeDto> list = service.getByTitle(title);
		map.addAttribute("list", list);
		return "Employee/list";
	}*/
}







