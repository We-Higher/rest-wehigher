package com.example.demo.board;

import java.util.Date;

import com.example.demo.member.Member;

import lombok.AllArgsConstructor;
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
	private String wdate;
	private String udate;
	private Member member;
	private String title;
	private String content;
	private int cnt;

	public static NotifyDto of(Notify notify) {
		return new NotifyDto(
				notify.getNum(),
				notify.getWdate(),
				notify.getUdate(),
				notify.getMember(),
				notify.getTitle(),
				notify.getContent(),
				notify.getCnt()
		);
	}
}
