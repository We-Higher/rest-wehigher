package com.example.demo.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_member", allocationSize = 1) // 시퀀스 생성. 생성한 시퀀스 이름: seq_board
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
    private Long id;
    @Column(unique = true)
    private String username; // 회원 ID
    private String pwd;
    private String name; // 이름
    private String email;
    private String phone; // 휴대전화
    private String address; // 거주지
    private String companyNamme; // 회사명
    private int deptCode; // 부서 코드
    private int companyRank; // 직급
//    @Column(unique = true)
    private int newNo; // 사번
    private String comCall; // 내선 전화
    @Column(columnDefinition = "int default 0", nullable = false)
    private int isMaster; // 관리자 여부. 0: 일반, 1: Master
    @Column(columnDefinition = "int default 1", nullable = false)
    private int status; // 사원 근무 여부?
    private String originFname; // 프로필 이미지 원본 파일명
    private String thumbnailFname; // 프로필 이미지 썸네일 파일명
//    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_newmemberno", allocationSize = 1) // 시퀀스 생성. 생성한 시퀀스 이름: seq_board
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_newmemberno")
    private int newMemNo; // 임시 테이블 사번

}
