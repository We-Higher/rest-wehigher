package com.example.demo.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto chatMessage, ModelMap map) {
//        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType()))
//            chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
    }

    @PostMapping("/chat/message/add")
    @ResponseBody
    public ChatMessage addMessage(ChatMessage chatMessage) {
        return chatMessageService.create(chatMessage);
    }
}