package com.example.demo.board;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.member.Member;
import com.example.demo.member.MemberDto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotifyDto {
	private int num;
	private Date wdate;
	private Date udate;
	private Member member;
	private String title;
	private String content;
}
