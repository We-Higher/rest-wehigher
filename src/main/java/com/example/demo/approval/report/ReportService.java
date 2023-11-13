package com.example.demo.approval.report;

import com.example.demo.approval.expense.Expense;
import com.example.demo.approval.expense.ExpenseDto;
import com.example.demo.approval.vacation.Vacation;
import com.example.demo.approval.vacation.VacationDto;
import com.example.demo.member.Member;
import com.example.demo.member.MemberDao;
import com.example.demo.member.MemberDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReportService {
    @Autowired
    private ReportDao dao;

    public ReportDto saveReport(ReportDto dto) {
    	
    	Report r = dao.save(new Report(dto.getReportNum(), dto.getMember(), dto.getTitle(), dto.getContent(), dto.getWdate(), dto.getServiceLife(), dto.getClassification(), dto.getStatus(), dto.getApproval1(), dto.getApproval2()));
    	return new ReportDto(r.getReportNum(), r.getMember(), r.getTitle(), r.getContent(), r.getWdate(), r.getServiceLife(), r.getClassification(), r.getStatus(), r.getApproval1(),r.getApproval2());
    }

    public ArrayList<ReportDto> getAll() {
        ArrayList<Report> list = (ArrayList<Report>) dao.findAll();
        ArrayList<ReportDto> list2 = new ArrayList<>();
        for (Report r : list) {
            list2.add(new ReportDto(r.getReportNum(), r.getMember(), r.getTitle(), r.getContent(), r.getWdate(), r.getServiceLife(), r.getClassification(), r.getStatus(), r.getApproval1(),r.getApproval2()));
        }
        return list2;
    }

    public ReportDto getById(int num) {
        Report r = dao.getById(num);
        return new ReportDto(r.getReportNum(), r.getMember(), r.getTitle(), r.getContent(), r.getWdate(), r.getServiceLife(), r.getClassification(), r.getStatus(), r.getApproval1(),r.getApproval2());
    }
    
    public ReportDto approveReport(ReportDto dto, MemberDto mdto) {
    	
    	if(dto.getStatus()==0 && dto.getApproval1().equals(mdto.getName())){
    		dto.setStatus(1);
    	}
    	else if(dto.getStatus()==1 && dto.getApproval2().equals(mdto.getName())){
    		dto.setStatus(2);
    	}
    	else System.out.println("결제할 수 없습니다.");
    	
    	Report r = dao.save(new Report(dto.getReportNum(), dto.getMember(), dto.getTitle(), dto.getContent(), dto.getWdate(), dto.getServiceLife(), dto.getClassification(), dto.getStatus(), dto.getApproval1(), dto.getApproval2()));
    	return new ReportDto(r.getReportNum(), r.getMember(), r.getTitle(), r.getContent(), r.getWdate(), r.getServiceLife(), r.getClassification(), r.getStatus(), r.getApproval1(),r.getApproval2());
    }
}
