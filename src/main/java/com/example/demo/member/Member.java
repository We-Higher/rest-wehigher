package com.example.demo.member;

import com.example.demo.member.dto.MemberJoinDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member implements UserDetails {
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
    private int cstatus; // 사원 출퇴근 관리. 0: 퇴근, 1: 출근
    private String originFname; // 프로필 이미지 원본 파일명
    private String thumbnailFname; // 프로필 이미지 썸네일 파일명
    private int newMemNo; // 임시 테이블 사번
    @Column(columnDefinition = "int default 15", nullable = false)
    private int remain; //연차 잔여일
    private int monthMember;

    // MemberDto -> Member
    public Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .username(memberDto.getUsername())
                .pwd(memberDto.getPwd())
                .name(memberDto.getName())
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
                .monthMember(memberDto.getMonthMember())
                .build();
    }

    // MemberJoinDto -> Member
    public Member toEntity (MemberJoinDto memberJoinDto) {
        return Member.builder()
                .username(memberJoinDto.getUsername())
                .pwd(memberJoinDto.getPwd())
                .name(memberJoinDto.getName())
                .email(memberJoinDto.getEmail())
                .phone(memberJoinDto.getPhone())
                .address(memberJoinDto.getAddress())
                .companyName(memberJoinDto.getCompanyName())
                .deptCode(memberJoinDto.getDeptCode())
                .companyRank(memberJoinDto.getCompanyRank())
                .newNo(memberJoinDto.getNewNo())
                .comCall(memberJoinDto.getComCall())
                .isMaster(memberJoinDto.getIsMaster())
                .status(memberJoinDto.getStatus())
                .build();
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한들을 담을 빈 리스트
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }

        if (isMaster == 1) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }


        return authorities;
    }

    // 사용자의 login id를 반환
    @Override
    public String getUsername() {
        return username;
    }

    // 사용자의 패스워드를 반환
    @Override
    public String getPassword() {
        return pwd;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // true : 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; // true : 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // true : 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true; // true : 사용 가능
    }
}
