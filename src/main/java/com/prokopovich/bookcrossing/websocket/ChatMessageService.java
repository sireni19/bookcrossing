package com.prokopovich.bookcrossing.websocket;

import com.mongodb.DuplicateKeyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void addMessage(ChatMessage message) throws DuplicateKeyException {
        chatMessageRepository.save(message);
    }
}
