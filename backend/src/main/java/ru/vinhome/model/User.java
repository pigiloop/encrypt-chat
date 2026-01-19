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
    /**
     * Идентификатор пользователя
     */
    private Long id;

    /**
     * Логин пользователя
     */
    @NonNull
    private String userName;

    /**
     * Электронная почта пользователя
     */
    private String email;

    /**
     * Имя пользователя
     */
    private String firstName;

    /**
     * Фамилия пользователя
     */
    private String lastName;

    /**
     * Пароль пользователя
     */
    private String password;

    /**
     * Возраст пользователя
     */
    private int age;
}
