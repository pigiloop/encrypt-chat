package ru.vinhome.service.integration;

import lombok.NonNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.postgresql.util.PSQLException;
import ru.vinhome.controller.dto.UserCreateRequest;
import ru.vinhome.controller.dto.UserUpdateRequest;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.service.UserServiceImpl;
import ru.vinhome.util.ConnectionUtil;
import ru.vinhome.util.PostgresTestContainer;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTestIntegration {

    private static ArrayList<User> users = null;

    @BeforeAll
    static void startContainer() {
        PostgresTestContainer.start();
        ConnectionUtil.reloadPool();
    }

    @AfterAll
    static void stopContainer() {
        PostgresTestContainer.stop();
    }

    @BeforeEach
    void createTable() throws SQLException, InterruptedException {

        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        userService.createTable();

        users = new ArrayList<>();

        users.add(new User(1, "user1", "klepeshkin@mail.ru", "Konstantin",
                "Lepeshkin", "qwerty", 18));

        users.add(new User(2, "user2", "nuskov@mail.ru", "Nikita",
                "Uskov", "qwerty", 25));

        users.add(new User(3, "user3", "cherepok@mail.ru", "Oleg",
                "Cherpanov", "qwerty", 23));

        for (User user : users) {
            userService.save(UserCreateRequest.mapFromUser(user));
        }
    }

    @AfterEach
    void dropTable() throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        userService.dropTable();
    }

    @ParameterizedTest
    @CsvSource({
            "4, kvin, ko@mail.ru, Konstantin, Vinogradov, passP123dssaaa, 18, 1, false",
            "6, plotnik, alex@mail.ru, Alexey, Lobanov, passP123dssaaa, 22, 1, false"
    })
    public void save(int id, @NonNull String userName, String email, String fName,
                     String lName, String password, int age, int result, Boolean isException) throws SQLException, InterruptedException {

        UserCreateRequest userCreateRequest = new UserCreateRequest(
                userName, email, fName, lName, password, age
        );

        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        if (isException) {
            Exception exception = assertThrows(PSQLException.class, () -> userService.save(userCreateRequest));
            Assertions.assertEquals("ERROR: duplicate key value violates unique constraint \"users_email_key\"\n"
                    + "  Detail: Key (email)=(ko@mail.ru) already exists.", exception.getMessage());
        } else {
            Assertions.assertEquals(result, userService.save(userCreateRequest));
        }
    }

    @Test
    public void findAllTest() throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        ArrayList<User> usersResult = userService.findAll();

        Assertions.assertEquals(users.get(0), usersResult.get(0));
        Assertions.assertEquals(users.get(1), usersResult.get(1));
        Assertions.assertEquals(users.get(2), usersResult.get(2));
    }

    @ParameterizedTest
    @CsvSource({
            "1, true",
            "2, true",
            "3, true",
            "16384, false"
    })
    public void findByIdTest(int id, String hasResult) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        User user = userService.findById(id);


        if (hasResult.equals("true")) {
            Assertions.assertEquals(users.get(id - 1), user);
        } else {
            Assertions.assertNull(user);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0, user1",
            "1, user2",
            "2, user3"
    })
    public void findByUsernameTestPositive(final int index, final String username) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        User user = userService.findByUsername(username);

        Assertions.assertEquals(users.get(index), user);
    }

    @ParameterizedTest
    @CsvSource({
            "failUser"
    })
    public void findByUsernameTestNegative(final String username) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        User user = userService.findByUsername(username);

        Assertions.assertNull(user);
    }

    @ParameterizedTest
    @CsvSource({
            "cherepok@mail.ru, true", "nuskov@mail.ru, true", "cherepok@mail.ru, true", "ko@mail.ru, false"
    })
    public void emailExistsTest(String email, String result) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Assertions.assertEquals(userService.emailExist(email), Boolean.valueOf(result));
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void deleteTest(int id) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        Assertions.assertEquals(
                1, userService.delete(id));
    }

    @ParameterizedTest
    @CsvSource({
            "1, update, 25",
            "2, update, 25",
            "3, update, 24"
    })
    public void updateTest(int id, String update, int age) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl userRepository = new JdbcUserRepositoryImpl();
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        User user = users.get(id - 1);

        user.setFirstName(update);
        user.setLastName(update);
        user.setAge(age);

        UserUpdateRequest userUpdateRequest =
                UserUpdateRequest.mapFromUser(user);

        Assertions.assertEquals(1, userService.update(id, userUpdateRequest));
        Assertions.assertEquals(
                users.get(id - 1),
                userService.findById(id)
        );
    }

}
