package com.example.demo.approval.expense;

import com.example.demo.approval.report.Report;
import com.example.demo.approval.report.ReportDao;
import com.example.demo.approval.report.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseDao dao;

    public ExpenseDto saveExpense(ExpenseDto dto) {
        Expense e = dao.save(new Expense(dto.getExpenseNum(),dto.getMember(),dto.getTitle(),dto.getContent(),dto.getWdate(),dto.getCategory(),dto.getDetail(),dto.getSum(),dto.getNote()));
        return new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote());
    }
}
