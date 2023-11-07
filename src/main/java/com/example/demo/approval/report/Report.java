package com.example.demo.approval.report;

import com.example.demo.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Report {
    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_report", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    private int reportNum; //품의서 고유번호

    @JoinColumn(nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;   //멤버
    private String title;   //제목
    private String content; //상세내용
    private Date wdate; //기안일
    private String serviceLife; //보존연한
    private String classification; //비밀등급

    @PrePersist
    public void setDate(){
        wdate = new Date();
    }
}
