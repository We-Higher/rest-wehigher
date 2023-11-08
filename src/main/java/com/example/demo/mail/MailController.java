package com.example.demo.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mservice;

    @GetMapping("/send")
    public void sendForm() {
    }

    @PostMapping("/send")
    public String sendMail(MailDto mailDto) {
        mservice.sendSimpleMessage(mailDto);
        System.out.println("메일 전송 완료");
        return "redirect:/main";
    }
}
