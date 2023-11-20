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
    @Autowired
    private MailReaderService mrservice;
    @Value("${spring.mail.username}")
    private String mailsender;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String pwd;

    @GetMapping("/send2")
    public void sendForm(Model map) {
        map.addAttribute("mailsender", mailsender);
    }

    @PostMapping("/send")
    public String sendMail(MailDto mailDto, MultipartFile file) throws MessagingException, IOException {
        mservice.sendMultipleMessage(mailDto, file);
        System.out.println("file = " + file);
        System.out.println("메일 전송 완료");
        return "/main";
    }

//    @GetMapping("/list")
//    public String emailList(Model model) {
//        /// 조회 기간 설정 (예: 최근 30일간의 이메일)
//        Calendar endDate = Calendar.getInstance();
//        Calendar startDate = (Calendar) endDate.clone();
//        startDate.add(Calendar.DAY_OF_MONTH, -30);
//
//        List<Mail> emailList = mrservice.receiveMailAttachedFile();
//        model.addAttribute("emails", emailList);
//        return "/mail/list";
//    }

    @GetMapping("/list")
    public String getEmailList(Model model) {
        // Gmail 계정 정보와 조회 기간 설정
        try {
            String userName = username;
            String password = pwd;
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-01");
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-30");
            // EmailReader 객체 생성
            MailReaderService mailReader = new MailReaderService();
            // 이메일 목록 가져오기
            List<Mail> emails = mailReader.receiveMailAttachedFile(userName, password, startDate, endDate);
            // Model에 이메일 목록 추가
            model.addAttribute("emails", emails);
        } catch (ParseException | MessagingException e) {
            throw new RuntimeException(e);
        }
        // Thymeleaf 템플릿 이름 반환
        return "/mail/list";
    }

}
