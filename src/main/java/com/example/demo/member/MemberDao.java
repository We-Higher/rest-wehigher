package com.example.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.employee.Employee;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MemberDao extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
    ArrayList<Member> findByNameLike(String name);//이름으로 검색
    ArrayList<Member> findByNewNoLike(String newNo);//사번으로 검색
    ArrayList<Member> findByDeptCodeLike(String deptCode);//부서코드로 검색
    ArrayList<Member> findByCompanyRankLike(String companyRank);//직급으로 검색
}
