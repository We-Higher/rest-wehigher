package com.example.demo.schedule;

import com.example.demo.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleDao extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByMember(Member member);

    List<Schedule> findByCnt(int cnt);
}
