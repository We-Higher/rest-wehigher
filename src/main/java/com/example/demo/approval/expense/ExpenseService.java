package com.example.demo.approval.expense;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseDao dao;

    public ExpenseDto saveExpense(ExpenseDto dto) {
        Expense e = dao.save(new Expense(dto.getExpenseNum(),dto.getMember(),dto.getTitle(),dto.getContent(),dto.getWdate(),dto.getCategory(),dto.getDetail(),dto.getSum(),dto.getNote(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApp1username(), dto.getApp2username()));
        return new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(), e.getRstatus(), e.getApproval1(),e.getApproval2(), e.getApp1username(), e.getApp2username());
    }

    public ArrayList<ExpenseDto> getAll(){
        ArrayList<Expense> list = (ArrayList<Expense>) dao.findAll();
        ArrayList<ExpenseDto> list2 = new ArrayList<>();
        for(Expense e : list){
            list2.add(new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(), e.getRstatus(), e.getApproval1(),e.getApproval2(), e.getApp1username(), e.getApp2username()));
        }
        return list2;
    }

    public ExpenseDto getById(int num){
        Expense e = dao.getById(num);
        return new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(), e.getRstatus(), e.getApproval1(),e.getApproval2(), e.getApp1username(), e.getApp2username());
    }

    public ArrayList<ExpenseDto> getByMember(Member m){
        ArrayList<Expense> list = dao.findByMember(m);
        ArrayList<ExpenseDto> list2 = new ArrayList<>();
        for(Expense e : list){
            list2.add(new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(), e.getRstatus(), e.getApproval1(),e.getApproval2(), e.getApp1username(), e.getApp2username()));
        }
        return list2;
    }
    
    public ExpenseDto approveExpense(ExpenseDto dto, MemberDto mdto) {
    	
    	if(dto.getRstatus()==0 && dto.getStatus()==0 && dto.getApp1username().equals(mdto.getUsername())){
    		dto.setStatus(1);
    	}
    	else if(dto.getRstatus()==0 && dto.getStatus()==1 && dto.getApp2username().equals(mdto.getUsername())){
    		dto.setStatus(2);
    	}
    	else System.out.println("결제할 수 없습니다.");
    	
    	Expense e = dao.save(new Expense(dto.getExpenseNum(),dto.getMember(),dto.getTitle(),dto.getContent(),dto.getWdate(),dto.getCategory(),dto.getDetail(),dto.getSum(),dto.getNote(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApp1username(), dto.getApp2username()));
        return new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(),e.getRstatus(),e.getApproval1(),e.getApproval2(), e.getApp1username(), e.getApp2username());
    }
    
    public ExpenseDto refuseExpense(ExpenseDto dto, MemberDto mdto) {
    	
    	if(dto.getRstatus()==0 && dto.getStatus()==0 && dto.getApp1username().equals(mdto.getUsername())){
    		dto.setRstatus(-1);
    	}
    	else if(dto.getRstatus()==0 && dto.getStatus()==1 && dto.getApp2username().equals(mdto.getUsername())){
    		dto.setRstatus(-1);
    	}
    	else System.out.println("반려할 수 없습니다.");
    	
    	Expense e = dao.save(new Expense(dto.getExpenseNum(),dto.getMember(),dto.getTitle(),dto.getContent(),dto.getWdate(),dto.getCategory(),dto.getDetail(),dto.getSum(),dto.getNote(), dto.getStatus(), dto.getRstatus(), dto.getApproval1(), dto.getApproval2(), dto.getApp1username(), dto.getApp2username()));
        return new ExpenseDto(e.getExpenseNum(),e.getMember(),e.getTitle(),e.getContent(),e.getWdate(),e.getCategory(),e.getDetail(),e.getSum(),e.getNote(),e.getStatus(),e.getRstatus(),e.getApproval1(),e.getApproval2(), e.getApp1username(), e.getApp2username());
    }
}
