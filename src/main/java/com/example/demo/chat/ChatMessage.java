package com.example.demo.chat;

import com.example.demo.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK,
    }
    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_chatmessage1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    private long id;
    // 메시지 타입 : 입장, 채팅
    private MessageType type; // 메시지 타입
    @ManyToOne
    private ChatRoom room; // 채팅방
    @ManyToOne
    private Member sender; // 메시지 보낸사람
    private String message; // 메시지
}