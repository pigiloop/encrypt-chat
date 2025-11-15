package ru.vinhome.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class User {
    @NonNull
    final private Long id;
    @NonNull
    final private String userName;
    final private String email;
    final private String firstName;
    final private String lastName;
    final private String password;
    final private int age;


    public User(@NonNull Long id, @NonNull String userName, String email, String firstName, String lastName, String password, int age) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
    }

    public static User setAge(final User user, final int age) {

        if (age < 18) {
            throw new RuntimeException("Детям в чат нельзя!!!");
        }

        return User.builder()
                .id(user.id)
                .userName(user.userName)
                .email(user.email)
                .firstName(user.firstName)
                .lastName(user.lastName)
                .age(user.age)
                .build();
    }

    public static User setFirstLastName(final User user, String firstName, String lastName) {

        if (firstName.isBlank() || lastName.isBlank()) {
            throw new RuntimeException("Имя пользователя не может быть пустым!!!");
        }

        return new User(user.getId(), user.getUserName(), user.getEmail(),
                firstName, lastName, user.getPassword(), user.getAge());
    }

}
