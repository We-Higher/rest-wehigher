package com.example.demo.approval.expense;

import com.example.demo.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Expense {
    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_expense", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    private int expenseNum; //품의서 고유번호

    @JoinColumn(nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;   //멤버
    private String title;   //제목
    private String content; //사유
    private String wdate; //작성일
    private String category; //분류
    private String detail; //내역
    private int sum; //금액
    private String note;    //비고
}
