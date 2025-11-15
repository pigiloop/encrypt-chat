package ru.vinhome.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Message {
    final private Long id;
    final private User sender;
    final private User recipient;
    final private String message;
    final private LocalDateTime createdAt;

    private Message(Long id, User sender, User recipient, String message, LocalDateTime createdAt) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.createdAt = createdAt;
    }


    public static Message createMessage(Long id,
                                        User sender,
                                        User recipient,
                                        String message) {
        return new Message(
                id,
                sender,
                recipient,
                message,
                null
        );
    }

}

