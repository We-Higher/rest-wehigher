package com.example.demo.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MemberDao extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
//    Member findByUsername(String username);
    Member findByName(String name);
    ArrayList<Member> findByNameLike(String name);//이름으로 검색
    ArrayList<Member> findByNewNoLike(String newNo);//사번으로 검색
    ArrayList<Member> findByDeptCodeLike(String deptCode);//부서코드로 검색
    ArrayList<Member> findByCompanyRankLike(String companyRank);//직급으로 검색

}
