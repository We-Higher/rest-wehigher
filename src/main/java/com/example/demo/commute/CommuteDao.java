package com.example.demo.commute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CommuteDao extends JpaRepository<Commute, Integer> {
    List<Commute> findByBasicDateLike(String BasicDate); //기준일로 검색

    List<Commute> findByMemberNameContaining(String name);  //이름으로 검색

    List<Commute> findByMemberDeptNameContaining(String deptName);  //부서명으로 검색

    List<Commute> findByMemberId(Long id);    //회원번호로 검색
    
    Commute findByBasicDateAndMemberUsername(String BasicDate, String Username);
}
