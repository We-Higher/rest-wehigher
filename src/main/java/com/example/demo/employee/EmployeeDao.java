package com.example.demo.employee;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;
import com.example.demo.employee.Employee;
@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
	
	//username으로 검색
	/*ArrayList<Employee> findByNameLike(String name);
	
	//deptCode로 검색
	ArrayList<Employee> findBydeptCodeLike(String deptCode);
	*/
}

