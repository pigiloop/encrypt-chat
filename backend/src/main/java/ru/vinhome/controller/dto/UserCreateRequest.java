package ru.vinhome.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.vinhome.model.User;

public record UserCreateRequest(

        @NotBlank(message = "username is required")
        @Size(min = 8, max = 50, message = "username must be more than 8 and less than 50")
        String userName,

        @NotBlank(message = "email is required")
        @Email(message = "this field must be email")
        @Size(min = 6, max = 50, message = "email must be more than 6 and less than 50")
        String email,

        @NotBlank(message = "firstname is required")
        @Size(min = 2, max = 50, message = "firstname must be more than 2 and less than 50")
        String firstName,

        @NotBlank(message = "lastname is required")
        @Size(min = 2, max = 50, message = "lastname must be more than 2 and less than 50")
        String lastName,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 50, message = "Password must be more than 8 and less than 50")
        String password,

        @Min(value = 18, message = "Age must be more or equals 18 year")
        @Max(value = 120, message = "Age must be less 120 year")
        int age) {

        public static UserCreateRequest mapFromUser(final User user) {
                return new UserCreateRequest(
                        user.getUserName(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPassword(),
                        user.getAge());
        }

}