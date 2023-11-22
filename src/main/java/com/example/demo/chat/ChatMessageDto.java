package com.example.demo.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChatMessageDto {
    private long id;
    // 메시지 타입 : 입장, 채팅
    private ChatMessage.MessageType type; // 메시지 타입
    private int roomId; // 채팅방
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
}
