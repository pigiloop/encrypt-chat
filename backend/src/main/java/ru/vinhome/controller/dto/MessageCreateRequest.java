package ru.vinhome.controller.dto;

//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import ru.vinhome.model.Message;


public record MessageCreateRequest(

//        @Min(value = 1, message = "ID sender must be less 1")
        int sender,

//        @Min(value = 1, message = "ID recipient must be less 1")
        int recipient,

//        @NotBlank(message = "Message is required")
//        @Size(max = 1024, message = "Password must be  less than 1024")
        String message) {

    public static MessageCreateRequest mapFromMessage(final Message message) {
        return new MessageCreateRequest(
                message.getSenderId(),
                message.getRecipientId(),
                message.getMessage());
    }
}


