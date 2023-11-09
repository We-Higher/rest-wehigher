package com.example.demo.approval.expense;

import com.example.demo.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseDao dao;

    public ExpenseDto saveExpense(ExpenseDto dto) {
        Expense e = dao.save(new Expense(dto.getExpenseNum(),dto.getMember(),dto.getTitle(),dto.getContent(),dto.getWdate(),dto.getCategory(),dto.getDetail(),dto.getSum(),dto.getNote(), dto.getStatus(),dto.getApproval1(), dto.getApproval2()));
        return new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(),e.getApproval1(),e.getApproval2());
    }

    public ArrayList<ExpenseDto> getAll(){
        ArrayList<Expense> list = (ArrayList<Expense>) dao.findAll();
        ArrayList<ExpenseDto> list2 = new ArrayList<>();
        for(Expense e : list){
            list2.add(new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(),e.getApproval1(),e.getApproval2()));
        }
        return list2;
    }

    public ExpenseDto getById(int num){
        Expense e = dao.getById(num);
        return new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(),e.getApproval1(),e.getApproval2());
    }

    public ArrayList<ExpenseDto> getByMember(Member m){
        ArrayList<Expense> list = dao.findByMember(m);
        ArrayList<ExpenseDto> list2 = new ArrayList<>();
        for(Expense e : list){
            list2.add(new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(),e.getApproval1(),e.getApproval2()));
        }
        return list2;
    }
}
