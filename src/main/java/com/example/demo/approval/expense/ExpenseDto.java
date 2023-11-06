package com.example.demo.approval.expense;

import com.example.demo.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpenseDto {
    private int expenseNum; //품의서 고유번호
    private Member member;   //멤버
    private String title;   //제목
    private String content; //사유
    private String wdate; //작성일
    private String category; //분류
    private String detail; //내역
    private int sum; //금액
    private String note;    //비고
}
