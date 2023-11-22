package com.example.demo.mail;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mservice;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String pwd;

    @GetMapping("/send")
    public void sendForm(Model map) {
        map.addAttribute("mailsender", username);
    }

    @PostMapping("/send")
    public String sendMail(MailDto mailDto, MultipartFile file) throws MessagingException, IOException {
        mservice.sendMultipleMessage(mailDto, file);
        System.out.println("file = " + file);
        System.out.println("메일 전송 완료");
        return "/main";
    }
}
