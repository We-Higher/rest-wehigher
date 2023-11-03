package com.example.demo.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberService service;

    @Test
    @DisplayName("관리자 회원가입")
    public void 관리자_회원가입() {
        service.create(MemberDto.builder()
                .username("admin")
                .pwd("1234")
                .name("관리자")
                .email("admin@email.com")
                .phone("010-1234-5678")
                .address("서울")
                .isMaster(1)

                .newNo(1)
                .deptCode(0)
                .companyRank(0)
                .build());
    }

    @Test
    @DisplayName("일반 회원가입")
    public void 일반_회원가입() {
        for (int i = 2; i < 12; i++) {
            service.create(MemberDto.builder()
                    .username("user" + i)
                    .pwd("1234")
                    .name("회원" + i)
                    .email("user" + i + "@email.com")
                    .phone("010-1234-5678")
                    .address("경기")

                    .newNo(i)
                    .deptCode(i % 3 + 1)
                    .companyRank(i % 4 + 1)
                    .build());
        }
    }
}
