package com.example.demo.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_chatroom1", allocationSize = 1) // 시퀀스 생성. 생성한 시퀀스 이름: seq_board
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    int id;
    private String roomId;
    private int memberId;

    public static ChatRoom create(int memberId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.memberId = memberId;
        return chatRoom;
    }
}