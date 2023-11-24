package com.example.demo.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MemberDao extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    //Member findByUsername(String username);
    Member findByName(String name);
    ArrayList<Member> findByNameLike(String name);  //이름으로 검색
    ArrayList<Member> findByNewNoLike(String newNo);  //사번으로 검색
    ArrayList<Member> findByCompanyRankNameLike(String companyRankName);  //직급으로 검색
    ArrayList<Member> findByDeptNameLike(String deptName);  //부서명으로 검색

    // 특정 사용자 제외 조회(채팅 초대)
    ArrayList<Member> findByIdNot(Long id);
}
