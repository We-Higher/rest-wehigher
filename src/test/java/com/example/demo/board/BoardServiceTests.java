package com.example.demo.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private BoardService service;

    /*@Test
    @DisplayName("일반 회원가입")
    public void 일반_회원가입() {
        for (int i = 2; i < 12; i++) {
            service.create(BoardDto.builder()
                    .title("title"+i)
                    .content("내용"+i)
                    .build());
        }
    }*/
}
