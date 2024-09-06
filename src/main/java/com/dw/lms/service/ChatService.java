package com.dw.lms.service;

import com.dw.lms.model.ChatMessage;
import com.dw.lms.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }
}
