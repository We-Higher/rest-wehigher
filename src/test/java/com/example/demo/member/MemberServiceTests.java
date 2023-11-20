package com.example.demo.member;

import com.example.demo.member.dto.MemberJoinDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberService service;

    @Test
    @DisplayName("관리자 회원가입")
    public void 관리자_회원가입() {
        service.create(MemberJoinDto.builder()
                .username("admin")
                .pwd("1234")
                .name("관리자")
                .email("admin@email.com")
                .phone("010-1234-5678")
                .address("서울")
                .companyName("Co-Operate-Works")
                .deptCode(1)
                .companyRank(9)
                .newNo("115284")
                .comCall("02-3415-2108")
                .isMaster(1)
                .status(0)
                .build());
    }

    @Transactional
    @Rollback(false)
    @Test
    @DisplayName("일반 회원가입")
    public void 일반_회원가입() {
        for (int i = 2; i < 30; i++) {
            service.create(MemberJoinDto.builder()
                    .username("user" + i)
                    .pwd("1234")
                    .name("회원" + i)
                    .email("user" + i + "@email.com")
                    .phone("010-1234-5678")
                    .address("경기")
                    .companyName("Co-Operate-Works")
                    .deptCode(i % 6 + 1)
                    .companyRank(i % 9 + 1)
                    .newNo("2023" + i)
                    .comCall("02-3415-2108")
                    .isMaster(0)
                    .status(0)
                    .build());
        }
    }
}
