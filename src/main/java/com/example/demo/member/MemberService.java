package com.example.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDao dao;

    public MemberDto create(MemberDto dto) {
        Member m = dao.save(
                Member.builder()
                        .username(dto.getUsername())
                        .pwd(dto.getPwd())
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .phone(dto.getPhone())
                        .address(dto.getAddress())

                        .newNo(dto.getNewNo())
                        .deptCode(dto.getDeptCode())
                        .companyRank(dto.getCompanyRank())
                        .build());
        return MemberDto.builder()
                .username(m.getUsername())
                .pwd(m.getPwd())
                .name(m.getName())
                .email(m.getEmail())
                .phone(m.getPhone())
                .address(m.getAddress())

                .newNo(m.getNewNo())
                .deptCode(m.getDeptCode())
                .companyRank(m.getCompanyRank())
                .build();
    }

    public MemberDto getMember(String id) {
        Member m = dao.findByUsername(id);
        if (m == null) {
            return null;
        }
        return MemberDto.builder()
                .id(m.getId())
                .deptCode(m.getDeptCode())
                .pwd(m.getPwd())
                .name(m.getName())
                .email(m.getEmail())
                .phone(m.getPhone())
                .address(m.getAddress())
                .companyNamme(m.getCompanyNamme())
                .deptCode(m.getDeptCode())
                .companyRank(m.getCompanyRank())
                .newNo(m.getNewNo())
                .comCall(m.getComCall())
                .isMaster(m.getIsMaster())
                .status(m.getStatus())
                .originFname(m.getOriginFname())
                .thumbnailFname(m.getThumbnailFname())
                .newMemNo(m.getNewMemNo())
                .build();
    }
}
