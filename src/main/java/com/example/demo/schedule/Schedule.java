package com.example.demo.schedule;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @SequenceGenerator(name="sch_gen", sequenceName="seq_schedule", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="sch_gen")
    private int id;
    private String title;
    private Date startDate;
    private Date endDate;

    @PrePersist
    public void setDate() {
        startDate = new Date();
        endDate = new Date();
    }

}
