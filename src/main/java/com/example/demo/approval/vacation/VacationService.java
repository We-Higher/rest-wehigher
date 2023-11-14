package com.example.demo.approval.vacation;

import com.example.demo.approval.expense.Expense;
import com.example.demo.approval.expense.ExpenseDto;
import com.example.demo.approval.report.Report;
import com.example.demo.approval.report.ReportDto;
import com.example.demo.member.MemberDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VacationService {
    @Autowired
    private VactaionDao dao;

    public VacationDto saveVacation(VacationDto dto) {
        Vacation v = dao.save(new Vacation(dto.getVacationNum(), dto.getMember(), dto.getType(), dto.getStartDate(), dto.getEndDate(), dto.getReason(), dto.getWdate(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApp1username(), dto.getApp2username()));
        return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApp1username(), v.getApp2username());
    }

    public ArrayList<VacationDto> getAll() {
        ArrayList<Vacation> list = (ArrayList<Vacation>) dao.findAll();
        ArrayList<VacationDto> list2 = new ArrayList<>();
        for (Vacation v : list) {
            list2.add(new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApp1username(), v.getApp2username()));
        }
        return list2;
    }

    public VacationDto getById(int num) {
        Vacation v = dao.getById(num);
        return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApp1username(), v.getApp2username());
    }
    
    public VacationDto approveVacation(VacationDto dto, MemberDto mdto) {
    	
    	if(dto.getRstatus()==0 && dto.getStatus()==0 && dto.getApp1username().equals(mdto.getUsername())){
    		dto.setStatus(1);
    	}
    	else if(dto.getRstatus()==0 &&dto.getStatus()==1 && dto.getApp2username().equals(mdto.getUsername())){
    		dto.setStatus(2);
    	}
    	else System.out.println("결제할 수 없습니다.");
    	
    	Vacation v = dao.save(new Vacation(dto.getVacationNum(), dto.getMember(), dto.getType(), dto.getStartDate(), dto.getEndDate(), dto.getReason(), dto.getWdate(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApp1username(), dto.getApp2username()));
        return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApp1username(), v.getApp2username());
    }
    
    public VacationDto refuseVacation(VacationDto dto, MemberDto mdto) {
    	
    	if(dto.getRstatus()==0 && dto.getStatus()==0 && dto.getApp1username().equals(mdto.getUsername())){
    		dto.setRstatus(-1);
    	}
    	else if(dto.getRstatus()==0 && dto.getStatus()==1 && dto.getApp2username().equals(mdto.getUsername())){
    		dto.setRstatus(-1);
    	}
    	else System.out.println("반려할 수 없습니다.");
    	
    	Vacation v = dao.save(new Vacation(dto.getVacationNum(), dto.getMember(), dto.getType(), dto.getStartDate(), dto.getEndDate(), dto.getReason(), dto.getWdate(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApp1username(), dto.getApp2username()));
        return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getStatus(), v.getRstatus(), v.getApproval1(), v.getApproval2(), v.getApp1username(), v.getApp2username());
    }
}
