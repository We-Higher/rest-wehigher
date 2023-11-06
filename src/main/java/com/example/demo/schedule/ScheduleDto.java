package com.example.demo.schedule;

import lombok.*;

import java.util.Date;
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private int id;
    private String title;
    private Date startDate;
    private Date endDate;
}
