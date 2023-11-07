package com.example.demo.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDao;
import com.example.demo.member.MemberDto;
import com.example.demo.employee.EmployeeDto;
import com.example.demo.employee.Employee;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeDao dao;
	@Autowired
	private MemberDao mdao;

	// 임직원 전체검색
	public ArrayList<EmployeeDto> getAll() {
		List<Member> list = mdao.findAll();
		ArrayList<EmployeeDto> list2 = new ArrayList<>();
		for (Member b : list) {
			list2.add(new EmployeeDto(b.getId(), b.getName(), b.getNewNo(), b.getDeptCode(), b.getCompanyRank(),
					b.getPhone(), b.getEmail(), b.getComCall()));
		}
		return list2;
	}

	// 옵션으로 검색
	public ArrayList<EmployeeDto> getByOption(String type, String option) {
		
		ArrayList<EmployeeDto> list2 = new ArrayList<>();
		if(type.equals("name")) {
			List<Member> list = mdao.findByNameLike("%" + option +"%");
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getNewNo(), b.getDeptCode(), b.getCompanyRank(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
			return list2;
		}
		else if(type.equals("newNo")) {
			int temp = Integer.parseInt(option);
			List<Member> list = mdao.findByNewNoLike("%" + temp +"%");
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getNewNo(), b.getDeptCode(), b.getCompanyRank(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
			return list2;
		}
		else if(type.equals("deptCode")) {
			List<Member> list = mdao.findByDeptCodeLike("%" + option +"%");
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getNewNo(), b.getDeptCode(), b.getCompanyRank(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
			return list2;
		}
		else if(type.equals("companyRank")) {
			List<Member> list = mdao.findByCompanyRankLike("%" + option +"%");
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getNewNo(), b.getDeptCode(), b.getCompanyRank(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
			return list2;
		}
		else {
			List<Member> list = mdao.findAll();
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getNewNo(), b.getDeptCode(), b.getCompanyRank(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
		}
		return list2;
	}

	// 추가, 수정. dao.save()
	/*
	 * public EmployeeDto saveEmployee(EmployeeDto b) { Employee b2 = dao.save(new
	 * Employee(b.getNum(), b.getWdate(), b.getWriter(), b.getTitle(),
	 * b.getContent())); return new EmployeeDto(b2.getNum(), b2.getWdate(),
	 * b2.getWriter(), b2.getTitle(), b2.getContent()); }
	 * 
	 * //pk로 검색. dao.findById() public EmployeeDto getEmployee(int num) { Employee b
	 * = dao.findById(num).orElse(null); return new EmployeeDto(b.getNum(),
	 * b.getWdate(), b.getWriter(), b.getTitle(), b.getContent()); }
	 * 
	 * 
	 * 
	 * //pk 기준 삭제. dao.deleteById() public void delEmployee(int num) {
	 * dao.deleteById(num); }
	 * 
	 * //작성자로 검색 public ArrayList<EmployeeDto> getByWriter(String writer){
	 * List<Employee> list = dao.findByWriter(new Member2(writer, "", "", ""));
	 * ArrayList<EmployeeDto> list2 = new ArrayList<>(); for(Employee b : list) {
	 * list2.add(new EmployeeDto(b.getNum(), b.getWdate(), b.getWriter(),
	 * b.getTitle(), b.getContent())); } return list2; }
	 */
}
