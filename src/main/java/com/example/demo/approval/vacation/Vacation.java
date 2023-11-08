package com.example.demo.approval.vacation;

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
public class Vacation {
    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_vacation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    private int vacationNum; //휴가신청서 고유번호

    @JoinColumn(nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;   //멤버
    private String type;   //휴가종류
    private String startDate; //휴가 시작일
    private String endDate; //휴가 종료일
    private String reason; //휴가사유
    private String wdate; //작성일
}
