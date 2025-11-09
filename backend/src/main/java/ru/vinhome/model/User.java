package ru.vinhome.model;

import lombok.NonNull;

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
/*
        return new User(user.getId(), user.getUserName(), user.getEmail(),
                user.getFirstName(), user.getLastName(),
                user.getPassword(), age);8

 */
        return User.builder()
                .id(user.getId())
                .userName(user.userName)
                .firstName(user.firstName)
                .lastName(user.getLastName())
                .email(user.getLastName())
                .age(user.getAge())
                .build();
    }

    public static User setFirstLastName(final User user, String firstName, String lastName) {

        if (firstName.isBlank() || lastName.isBlank()) {
            throw new RuntimeException("Имя пользователя не может быть пустым!!!");
        }

        return new User(user.getId(), user.getUserName(), user.getEmail(),
                firstName, lastName, user.getPassword(), user.getAge());
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public @NonNull Long getId() {
        return this.id;
    }

    public @NonNull String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public int getAge() {
        return this.age;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        if (this.getAge() != other.getAge()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        result = result * PRIME + this.getAge();
        return result;
    }

    public String toString() {
        return "User(id=" + this.getId() + ", userName=" + this.getUserName() + ", email=" + this.getEmail() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", password=" + this.getPassword() + ", age=" + this.getAge() + ")";
    }

    public static class UserBuilder {
        private @NonNull Long id;
        private @NonNull String userName;
        private String email;
        private String firstName;
        private String lastName;
        private String password;
        private int age;

        UserBuilder() {
        }

        public UserBuilder id(@NonNull Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder userName(@NonNull String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder age(@NonNull int age) {
            this.age = age;
            return this;
        }

        public User build() {
            return new User(this.id, this.userName, this.email, this.firstName, this.lastName, this.password, this.age);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", userName=" + this.userName + ", email=" + this.email + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", password=" + this.password + ", age=" + this.age + ")";
        }
    }
}
