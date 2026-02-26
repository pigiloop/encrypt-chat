package ru.vinhome.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.vinhome.model.User;

public record UserUpdateRequest(
         @NotBlank(message = "firstname is required")
        @Size(min = 2, max = 50, message = "firstname must be more than 2 and less than 50")
        String firstName,

        @NotBlank(message = "lastname is required")
        @Size(min = 2, max = 50, message = "lastname must be more than 2 and less than 50")
        String lastName,

        @Min(value = 18, message = "Age must be more or equals 18 year")
        @Max(value = 120, message = "Age must be less 120 year")
        int age) {
        public static UserUpdateRequest mapFromUser(final User user) {
                return new UserUpdateRequest(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getAge());
        }
}
