package com.example.demo.employee;

import com.example.demo.commute.Commute;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDao;
import com.example.demo.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private MemberDao mdao;

    // 임직원 전체검색
    public ArrayList<EmployeeDto> getAll() {
        List<Member> list = mdao.findAll();
        ArrayList<EmployeeDto> list2 = new ArrayList<>();
        for (Member b : list) {
            list2.add(new EmployeeDto(b.getId(), b.getName(), b.getUsername(), b.getNewNo(), b.getDeptCode(), b.getDeptName(), b.getCompanyRank(), b.getCompanyRankName(),
                    b.getPhone(), b.getEmail(), b.getComCall()));
        }
        return list2;
    }

    // 옵션으로 검색
    public Page<MemberDto> getByOption(String type, String option, Pageable pageable) {
        Page<Member> list;
        if (type.equals("name")) {
            list = mdao.findByNameLike("%" + option + "%", pageable);
        } else if (type.equals("newNo")) {
            list = mdao.findByNewNoLike("%" + option + "%", pageable);
        } else if (type.equals("companyRankName")) {
            list = mdao.findByCompanyRankNameLike("%" + option + "%", pageable);
        } else if (type.equals("deptName")) {
            list = mdao.findByDeptNameLike("%" + option + "%", pageable);
        } else {
            list = mdao.findAll(pageable);
        }
        return list.map(MemberDto::of);
    }

}
