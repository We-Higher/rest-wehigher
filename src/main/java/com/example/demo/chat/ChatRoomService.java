package com.example.demo.chat;

import com.example.demo.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomDao chatRoomDao;

    public ChatRoom create(ChatRoom chatRoom) {
        return chatRoomDao.save(chatRoom);
    }
    public ChatRoom participate(ChatRoom chatRoom, Member member) {
        chatRoom.getParticipants().add(member);
        return chatRoomDao.save(chatRoom);
    }

    public List<ChatRoom> getByParticipantId(long participantId) {
        return chatRoomDao.findByParticipants_Id(participantId);
    }

}
