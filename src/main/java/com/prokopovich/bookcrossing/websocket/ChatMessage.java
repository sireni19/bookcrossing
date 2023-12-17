package com.prokopovich.bookcrossing.websocket;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "chat_messages")
@ToString
public class ChatMessage {
    @Id
    private String id;
    private String content;
    private String sender;
    private MessageType type;
    private LocalDateTime time;

    public ChatMessage(String content, String sender, MessageType type, LocalDateTime time) {
        this.content = content;
        this.sender = sender;
        this.type = type;
        this.time = time;
    }
}
