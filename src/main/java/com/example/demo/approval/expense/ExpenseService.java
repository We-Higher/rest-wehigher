package com.example.demo.approval.expense;

import com.example.demo.approval.report.Report;
import com.example.demo.approval.report.ReportDao;
import com.example.demo.approval.report.ReportDto;
import com.example.demo.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseDao dao;

    public ExpenseDto saveExpense(ExpenseDto dto) {
        Expense e = dao.save(new Expense(dto.getExpenseNum(),dto.getMember(),dto.getTitle(),dto.getContent(),dto.getWdate(),dto.getCategory(),dto.getDetail(),dto.getSum(),dto.getNote(), dto.getStatus()));
        return new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus());
    }

    public ArrayList<ExpenseDto> getAll(){
        ArrayList<Expense> list = (ArrayList<Expense>) dao.findAll();
        ArrayList<ExpenseDto> list2 = new ArrayList<>();
        for(Expense e : list){
            list2.add(new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus()));
        }
        return list2;
    }

    public ExpenseDto getById(int num){
        Expense e = dao.getById(num);
        return new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus());
    }
}
