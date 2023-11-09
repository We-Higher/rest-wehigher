package com.example.demo.approval.vacation;

import com.example.demo.approval.expense.Expense;
import com.example.demo.approval.expense.ExpenseDto;
import com.example.demo.approval.report.Report;
import com.example.demo.approval.report.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VacationService {
    @Autowired
    private VactaionDao dao;

    public VacationDto saveVacation(VacationDto dto) {
        Vacation v = dao.save(new Vacation(dto.getVacationNum(), dto.getMember(), dto.getType(), dto.getStartDate(), dto.getEndDate(), dto.getReason(), dto.getWdate(), dto.getApproval1(), dto.getApproval2()));
        return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getApproval1(), v.getApproval2());
    }

    public ArrayList<VacationDto> getAll() {
        ArrayList<Vacation> list = (ArrayList<Vacation>) dao.findAll();
        ArrayList<VacationDto> list2 = new ArrayList<>();
        for (Vacation v : list) {
            list2.add(new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getApproval1(), v.getApproval2()));
        }
        return list2;
    }

    public VacationDto getById(int num) {
        Vacation v = dao.getById(num);
        return new VacationDto(v.getVacationNum(), v.getMember(), v.getType(), v.getStartDate(), v.getEndDate(), v.getReason(), v.getWdate(), v.getApproval1(), v.getApproval2());
    }
}
