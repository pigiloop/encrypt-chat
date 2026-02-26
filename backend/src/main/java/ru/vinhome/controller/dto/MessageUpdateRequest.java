package ru.vinhome.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MessageUpdateRequest(
        @NotBlank(message = "Message is required")
        @Size(max = 1024, message = "Password must be  less than 1024")
        String message) {


}
