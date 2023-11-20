package com.example.demo.schedule;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDto {
    private int id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int cnt;
    private String className;
}
