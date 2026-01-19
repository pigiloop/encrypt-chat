package ru.vinhome.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс модели Message.
 * В этом классе реализуется модель Message. В нём реализована структура сообщения
 *
 */
@Data
@Builder
@NoArgsConstructor
public class Message {
    /**
     * Идентификатор сообщения
     */
    private Long id;
    /**
     * Отправитель сообщения
     */
    private User sender;

    /**
     * Получатель сообщения
     */
    private User recipient;

    /**
     * Текст сообщения
     */
    private String message;

    /**
     * Дата создания сообщения
     */
    private LocalDateTime createdAt;


    /**
     * Приватный конструктор класса Message.
     * @param id уникальный идентификатор сообщения
     * @param sender отправитель сообщения
     * @param recipient получатель сообщения
     * @param message текст сообщения
     * @param createdAt дата и время создания сообщения
     */
    private Message(Long id, User sender, User recipient, String message, LocalDateTime createdAt) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.createdAt = createdAt;
    }

    /**
     * Статический метод создаёт экземпляр объекта класса Message.
     * @param id уникальный идентификатор сообщения
     * @param sender отправитель сообщения
     * @param recipient получатель сообщения
     * @param message текст сообщения
     * @param createdAt дата и время создания сообщения
     * @return Экземпляр объекта класса Message
     */
    public static Message createMessage(Long id,
                                        User sender,
                                        User recipient,
                                        String message,
                                        LocalDateTime createdAt) {
        return new Message(
                id,
                sender,
                recipient,
                message,
                createdAt
        );
    }

}

