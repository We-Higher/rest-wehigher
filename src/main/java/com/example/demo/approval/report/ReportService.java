package com.example.demo.approval.report;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportDao dao;

    public ReportDto saveReport(ReportDto dto) {
        Report r = dao.save(new Report(dto.getReportNum(), dto.getMember(), dto.getTitle(), dto.getContent(), dto.getWdate(), dto.getServiceLife(), dto.getClassification()));
        return new ReportDto(r.getReportNum(), r.getMember(), r.getTitle(), r.getContent(), r.getWdate(), r.getServiceLife(), r.getClassification());
    }
}
