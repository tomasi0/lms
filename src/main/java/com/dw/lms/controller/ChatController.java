package com.dw.lms.controller;

import com.dw.lms.model.ChatMessage;
import com.dw.lms.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Controller
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {
        // 메시지를 HTML 인코딩하여 저장
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        // 메시지를 DB에 저장
        chatService.saveMessage(message);
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage message) {
        message.setContent(HtmlUtils.htmlEscape(message.getSender()) + " joined!");
        // 새 유저 정보는 저장하지 않지만 필요시 추가 가능
        return message;
    }

    // REST API로 모든 메시지 조회 (필요한 경우)
    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return chatService.getAllMessages();
    }
}
