package com.example.demo.chat;

import com.example.demo.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @SequenceGenerator(name = "seq_gen", sequenceName = "seq_chatroom1", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    int id;
    private String roomId;
    private String roomName;

    @ManyToMany
    Set<Member> participants;

//    public static ChatRoom create(int memberId) {
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.roomId = UUID.randomUUID().toString();
//        return chatRoom;
//    }
}