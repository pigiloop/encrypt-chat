package ru.vinhome.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Класс модели User.
 * В этом классе реализуется модель User. В нём реализована структура хранения данных о пользователе.
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NonNull
    private Long id;
    @NonNull
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private int age;

}
