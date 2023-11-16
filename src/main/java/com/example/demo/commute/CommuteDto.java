package com.example.demo.commute;

import com.example.demo.member.Member;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommuteDto {
    private int commuteNum; //근태관리 번호
    private Member member;   //멤버
    private String basicDate;    //기준일
    private String startTime;   //출근시간
    private String endTime;     //퇴근시간
    private String reason;     //수정사유
    private String editStartTime;   //수정 출근시간
    private String editEndTime;     //수정 퇴근시간
    private String editBasicDate;   //수정 기준일
}
