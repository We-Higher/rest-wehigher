package com.example.demo.mail;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String from;
    private String[] address;
    private String[] ccAddress;
    private String title;
    private String content;
}
