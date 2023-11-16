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
			list2.add(new EmployeeDto(b.getId(), b.getName(), b.getUsername(), b.getNewNo(), b.getDeptCode(), b.getDeptName(), b.getCompanyRank(), b.getCompanyRankName(),
					b.getPhone(), b.getEmail(), b.getComCall()));
		}
		return list2;
	}

	// 옵션으로 검색
	public ArrayList<EmployeeDto> getByOption(String type, String option) {
		
		ArrayList<EmployeeDto> list2 = new ArrayList<>();
		if(type.equals("name")){
			List<Member> list = mdao.findByNameLike("%" + option +"%");
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getUsername(), b.getNewNo(), b.getDeptCode(), b.getDeptName(), b.getCompanyRank(), b.getCompanyRankName(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
			return list2;
		}
		else if(type.equals("newNo")) {
			List<Member> list = mdao.findByNewNoLike("%" + option +"%");
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getUsername(), b.getNewNo(), b.getDeptCode(), b.getDeptName(), b.getCompanyRank(), b.getCompanyRankName(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
			return list2;
		}
		else if(type.equals("companyRankName")) {
			List<Member> list = mdao.findByCompanyRankNameLike("%" + option +"%");
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getUsername(), b.getNewNo(), b.getDeptCode(), b.getDeptName(), b.getCompanyRank(), b.getCompanyRankName(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
			return list2;
		}
		else if(type.equals("deptName")) {
			List<Member> list = mdao.findByDeptNameLike("%" + option +"%");
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getUsername(), b.getNewNo(), b.getDeptCode(), b.getDeptName(), b.getCompanyRank(), b.getCompanyRankName(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
			return list2;
		}
		else {
			List<Member> list = mdao.findAll();
			for (Member b : list) {
				list2.add(new EmployeeDto(b.getId(), b.getName(), b.getUsername(), b.getNewNo(), b.getDeptCode(), b.getDeptName(), b.getCompanyRank(), b.getCompanyRankName(),
						b.getPhone(), b.getEmail(), b.getComCall()));
			}
			return list2;
		}
	}
}
