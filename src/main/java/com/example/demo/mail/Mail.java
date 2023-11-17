package com.example.demo.mail;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Mail {
    private Long id;
    private String from;
    private String subject;
    private Date receivedDate;


    // 생성자, getter, setter 등 필요한 메소드 추가

    // 예시로 몇 개의 이메일을 생성하는 메소드
    public static List<Mail> createEmails() {
        List<Mail> emails = new ArrayList<>();
        emails.add(new Mail(1L, "example1@example.com", "Subject 1", new Date()));
        emails.add(new Mail(2L, "example2@example.com", "Subject 2", new Date()));
        // 나머지 이메일들도 추가

        return emails;
    }
}
