package com.example.demo.mail;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailDto {
    private String address;
    private String title;
    private String content;
}