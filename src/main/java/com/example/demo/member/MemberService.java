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
    	
    	if(dto.getDeptCode()==0) dto.setDeptName("총무팀");
    	else if(dto.getDeptCode()==1) dto.setDeptName("인사팀");
    	else if(dto.getDeptCode()==2) dto.setDeptName("법무팀");
    	else if(dto.getDeptCode()==3) dto.setDeptName("마케팅팀");
    	else if(dto.getDeptCode()==4) dto.setDeptName("인프라 서비스팀");
    	else if(dto.getDeptCode()==5) dto.setDeptName("데이터 서비스팀");
    	else if(dto.getDeptCode()==6) dto.setDeptName("네트워크 서비스팀");
    	
    	if(dto.getCompanyRank()==1) dto.setCompanyRankName("사원");
    	else if(dto.getCompanyRank()==2) dto.setCompanyRankName("대리");
    	else if(dto.getCompanyRank()==3) dto.setCompanyRankName("과장");
    	else if(dto.getCompanyRank()==4) dto.setCompanyRankName("차장");
    	else if(dto.getCompanyRank()==5) dto.setCompanyRankName("부장");
    	else if(dto.getCompanyRank()==6) dto.setCompanyRankName("상무");
    	else if(dto.getCompanyRank()==7) dto.setCompanyRankName("전무");
    	else if(dto.getCompanyRank()==8) dto.setCompanyRankName("대표이사");
    	else if(dto.getCompanyRank()==9) dto.setCompanyRankName("회장");
    	
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
                        .deptName(dto.getDeptName())
                        .isMaster(dto.getIsMaster())
                        .companyName(dto.getCompanyName())
                        .companyRank(dto.getCompanyRank())
                        .companyRankName(dto.getCompanyRankName())
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
                .deptName(m.getDeptName())
                .isMaster(m.getIsMaster())
                .companyName(m.getCompanyName())
                .companyRank(m.getCompanyRank())
                .companyRankName(m.getCompanyRankName())
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
                .deptName(m.getDeptName())
                .companyRank(m.getCompanyRank())
                .companyRankName(m.getCompanyRankName())
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
                .deptName(m.getDeptName())
                .companyRank(m.getCompanyRank())
                .companyRankName(m.getCompanyRankName())
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
    	
    	if(dto.getDeptCode()==0) dto.setDeptName("총무팀");
    	else if(dto.getDeptCode()==1) dto.setDeptName("인사팀");
    	else if(dto.getDeptCode()==2) dto.setDeptName("법무팀");
    	else if(dto.getDeptCode()==3) dto.setDeptName("마케팅팀");
    	else if(dto.getDeptCode()==4) dto.setDeptName("인프라 서비스팀");
    	else if(dto.getDeptCode()==5) dto.setDeptName("데이터 서비스팀");
    	else if(dto.getDeptCode()==6) dto.setDeptName("네트워크 서비스팀");
    	
    	if(dto.getCompanyRank()==1) dto.setCompanyRankName("사원");
    	else if(dto.getCompanyRank()==2) dto.setCompanyRankName("대리");
    	else if(dto.getCompanyRank()==3) dto.setCompanyRankName("과장");
    	else if(dto.getCompanyRank()==4) dto.setCompanyRankName("차장");
    	else if(dto.getCompanyRank()==5) dto.setCompanyRankName("부장");
    	else if(dto.getCompanyRank()==6) dto.setCompanyRankName("상무");
    	else if(dto.getCompanyRank()==7) dto.setCompanyRankName("전무");
    	else if(dto.getCompanyRank()==8) dto.setCompanyRankName("대표이사");
    	else if(dto.getCompanyRank()==9) dto.setCompanyRankName("회장");
    	
        Member m = dao.save(new Member(dto.getId(),dto.getUsername(),dto.getPwd(),dto.getName(),dto.getEmail(),dto.getPhone(),dto.getAddress(),dto.getCompanyName(),dto.getDeptCode(),dto.getDeptName(),dto.getCompanyRank(),dto.getCompanyRankName(),dto.getNewNo(),dto.getComCall(),dto.getIsMaster(), dto.getStatus(), dto.getCstatus(), dto.getOriginFname(),dto.getThumbnailFname(),dto.getNewMemNo(),dto.getRemain()));
        return new MemberDto(m.getId(),m.getUsername(),m.getPwd(),m.getName(),m.getEmail(),m.getPhone(),m.getAddress(),m.getCompanyName(),m.getDeptCode(),m.getDeptName(),m.getCompanyRank(),m.getCompanyRankName(),m.getNewNo(),m.getComCall(),m.getIsMaster(),m.getStatus(), m.getCstatus(), m.getOriginFname(),m.getThumbnailFname(),m.getNewMemNo(),m.getRemain());
    }

    public void delete(Long id){
       dao.deleteById(id);
    }
}

