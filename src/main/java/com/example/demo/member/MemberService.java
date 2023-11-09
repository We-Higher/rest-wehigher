package com.example.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao dao;
    private final PasswordEncoder passwordEncoder;

    public MemberDto create(MemberDto dto) {
        Member m = dao.save(
                Member.builder()
                        .username(dto.getUsername())
                        .pwd(passwordEncoder.encode(dto.getPwd()))
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .phone(dto.getPhone())
                        .address(dto.getAddress())
                        .comCall(dto.getComCall())
                        .newNo(dto.getNewNo())
                        .status(dto.getStatus())
                        .deptCode(dto.getDeptCode())
                        .isMaster(dto.getIsMaster())
                        .companyName(dto.getCompanyName())
                        .companyRank(dto.getCompanyRank())
                        .remain(15)
                        .build());
        return MemberDto.builder()
                .username(m.getUsername())
                .pwd(m.getPwd())
                .name(m.getName())
                .email(m.getEmail())
                .phone(m.getPhone())
                .address(m.getAddress())
                .comCall(m.getComCall())
                .newNo(m.getNewNo())
                .status(m.getStatus())
                .deptCode(m.getDeptCode())
                .isMaster(m.getIsMaster())
                .companyName(m.getCompanyName())
                .companyRank(m.getCompanyRank())
                .remain(m.getRemain())
                .build();
    }

    public MemberDto getMember(String username) {
        Member m = dao.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return MemberDto.builder()
                .username(m.getUsername())
                .id(m.getId())
                .deptCode(m.getDeptCode())
                .pwd(m.getPwd())
                .name(m.getName())
                .email(m.getEmail())
                .phone(m.getPhone())
                .address(m.getAddress())
                .companyName(m.getCompanyName())
                .deptCode(m.getDeptCode())
                .companyRank(m.getCompanyRank())
                .newNo(m.getNewNo())
                .comCall(m.getComCall())
                .isMaster(m.getIsMaster())
                .status(m.getStatus())
                .originFname(m.getOriginFname())
                .thumbnailFname(m.getThumbnailFname())
                .newMemNo(m.getNewMemNo())
                .remain(m.getRemain())
                .build();
    }

    public MemberDto getMemberByName(String name) {
        Member m = dao.findByName(name);
        if (m == null) {
            return null;
        }
        return MemberDto.builder()
                .username(m.getUsername())
                .id(m.getId())
                .deptCode(m.getDeptCode())
                .pwd(m.getPwd())
                .name(m.getName())
                .email(m.getEmail())
                .phone(m.getPhone())
                .address(m.getAddress())
                .companyName(m.getCompanyName())
                .deptCode(m.getDeptCode())
                .companyRank(m.getCompanyRank())
                .newNo(m.getNewNo())
                .comCall(m.getComCall())
                .isMaster(m.getIsMaster())
                .status(m.getStatus())
                .originFname(m.getOriginFname())
                .thumbnailFname(m.getThumbnailFname())
                .newMemNo(m.getNewMemNo())
                .remain(m.getRemain())
                .build();
    }

    public MemberDto save(MemberDto dto) {
        Member m = dao.save(new Member(dto.getId(),dto.getUsername(),dto.getPwd(),dto.getName(),dto.getEmail(),dto.getPhone(),dto.getAddress(),dto.getCompanyName(),dto.getDeptCode(),dto.getCompanyRank(),dto.getNewNo(),dto.getComCall(),dto.getIsMaster(),dto.getStatus(),dto.getOriginFname(),dto.getThumbnailFname(),dto.getNewMemNo(),dto.getRemain()));
        return new MemberDto(m.getId(),m.getUsername(),m.getPwd(),m.getName(),m.getEmail(),m.getPhone(),m.getAddress(),m.getCompanyName(),m.getDeptCode(),m.getCompanyRank(),m.getNewNo(),m.getComCall(),m.getIsMaster(),m.getStatus(),m.getOriginFname(),m.getThumbnailFname(),m.getNewMemNo(),m.getRemain());
    }

    public void delete(Long id){
       dao.deleteById(id);
    }
}

