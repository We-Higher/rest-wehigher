package com.example.demo.member;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String username; // 회원 ID
    private String pwd;
    private String name; // 이름
    private String email;
    private String phone; // 휴대전화
    private String address; // 거주지
    private String companyName; // 회사명
    private int deptCode; // 부서 코드
    private int companyRank; // 직급
    private int newNo; // 사번
    private String comCall; // 내선 전화
    private int isMaster; // 관리자 여부. 0: 일반, 1: Master
    private int status; // 사원 근무 여부?
    private String originFname; // 프로필 이미지 원본 파일명
    private String thumbnailFname; // 프로필 이미지 썸네일 파일명
    private int newMemNo; // 임시 테이블 사번
    // TODO multipart 추가
}
