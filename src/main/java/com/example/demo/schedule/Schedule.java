package com.example.demo.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @SequenceGenerator(name="sch_gen", sequenceName="seq_schedule1", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="sch_gen")
    private int id;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(columnDefinition = "int default 0", nullable = false)
    private int cnt;
    private String className;
}
