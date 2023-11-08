package com.example.demo.dataroom;

import com.example.demo.member.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DataroomDto {
    private int num;
    private Member writer;
    private Date wdate;
    private String title;
    private String content;
    private String fname;
    private int cnt;
    private MultipartFile f;
}
