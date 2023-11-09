package com.example.demo.dataroom;

import com.example.demo.member.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date wdate;
    private String title;
    private String content;
    private String fname;
    private int cnt;
    private MultipartFile f;

    public static DataroomDto of(Dataroom dataroom) {
        return new DataroomDto(
                dataroom.getNum(),
                dataroom.getWriter(),
                dataroom.getWdate(),
                dataroom.getTitle(),
                dataroom.getContent(),
                dataroom.getFname(),
                dataroom.getCnt(),
                null
        );
    }

}
