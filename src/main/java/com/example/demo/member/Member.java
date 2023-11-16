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
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_member1", allocationSize = 1)
    // 시퀀스 생성. 생성한 시퀀스 이름: seq_board
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    private Long id;
    @Column(unique = true)
    private String username; // 회원 ID
    private String pwd;
    private String name; // 이름
    private String email;
    private String phone; // 휴대전화
    private String address; // 거주지
    private String companyName; // 회사명
    private int deptCode; // 부서 코드
    private String deptName; // 부서명
    private int companyRank; // 직급
    private String companyRankName; // 직급명
    private String newNo; // 사번
    private String comCall; // 내선 전화
    @Column(columnDefinition = "int default 0", nullable = false)
    private int isMaster; // 관리자 여부. 0: 일반, 1: Master
    @Column(columnDefinition = "int default 1", nullable = false)
    private int status; // 사원 근무 여부. 0: 재직 , 1:퇴사
    private int cstatus; // 사원 출퇴근 관리. 0: 출근, 1: 퇴근
    private String originFname; // 프로필 이미지 원본 파일명
    private String thumbnailFname; // 프로필 이미지 썸네일 파일명
    private int newMemNo; // 임시 테이블 사번
    @Column(columnDefinition = "int default 15", nullable = false)
    private int remain; //연차 잔여일

    public Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .username(memberDto.getUsername())
                .pwd(memberDto.getPwd())
                .email(memberDto.getEmail())
                .phone(memberDto.getPhone())
                .address(memberDto.getAddress())
                .companyName(memberDto.getCompanyName())
                .deptCode(memberDto.getDeptCode())
                .deptName(memberDto.getDeptName())
                .companyRank(memberDto.getCompanyRank())
                .companyRankName(memberDto.getCompanyRankName())
                .newNo(memberDto.getNewNo())
                .comCall(memberDto.getComCall())
                .isMaster(memberDto.getIsMaster())
                .status(memberDto.getStatus())
                .cstatus(memberDto.getCstatus())
                .originFname(memberDto.getOriginFname())
                .thumbnailFname(memberDto.getThumbnailFname())
                .newMemNo(memberDto.getNewMemNo())
                .remain(memberDto.getRemain())
                .build();
    }
}
