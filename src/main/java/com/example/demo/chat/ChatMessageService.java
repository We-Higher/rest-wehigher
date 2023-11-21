package com.example.demo.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final MessageDao messageDao;


    public ChatMessage create(ChatMessage chatMessage) {
        return messageDao.save(chatMessage);
    }
}
