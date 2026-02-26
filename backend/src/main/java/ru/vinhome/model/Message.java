package ru.vinhome.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private int id;
    /**
     * Отправитель сообщения
     */
    private int senderId;

    /**
     * Получатель сообщения
     */
    private int recipientId;

    /**
     * Текст сообщения
     */
    private String message;

    /**
     * Дата создания сообщения
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;


    /**
     * Приватный конструктор класса Message.
     * @param id уникальный идентификатор сообщения
     * @param senderId отправитель сообщения
     * @param recipientId получатель сообщения
     * @param message текст сообщения
     * @param createdAt дата и время создания сообщения
     */
    private Message(int id, int senderId, int recipientId, String message, LocalDateTime createdAt) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.message = message;
        this.createdAt = createdAt;
    }

    /**
     * Статический метод создаёт экземпляр объекта класса Message.
     * @param id уникальный идентификатор сообщения
     * @param senderId отправитель сообщения
     * @param recipientId получатель сообщения
     * @param message текст сообщения
     * @param createdAt дата и время создания сообщения
     * @return Экземпляр объекта класса Message
     */
    public static Message createMessage(int id,
                                        int senderId,
                                        int recipientId,
                                        String message,
                                        LocalDateTime createdAt) {
        return new Message(
                id,
                senderId,
                recipientId,
                message,
                createdAt
        );
    }

}

