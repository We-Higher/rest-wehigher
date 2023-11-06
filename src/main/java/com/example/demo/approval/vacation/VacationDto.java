package com.example.demo.approval.vacation;

import com.example.demo.member.Member;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VacationDto {
    private int vacationNum; //휴가신청서 고유번호
    private Member member;   //멤버
    private String type;   //휴가종류
    private String startDate; //상세내용
    private String endDate; //상세내용
    private String reason; //휴가사유
}
