package ru.vinhome.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.vinhome.controller.dto.UserCreateRequest;
import ru.vinhome.controller.dto.UserUpdateRequest;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;


public class UserServiceTestUnit {

    private static ArrayList<User> users = null;

    @BeforeAll
    public static void fillUsersArrayList() {
        users = new ArrayList<>();

        users.add(new User(1, "user1", "klepeshkin@mail.ru", "Konstantin",
                "Lepeshkin", "qwerty", 18));

        users.add(new User(2, "user2", "nuskov@mail.ru", "Nikita",
                "Uskov", "qwerty", 25));

        users.add(new User(3, "user3", "cherepok@mail.ru", "Oleg",
                "Cherpanov", "qwerty", 23));
    }

    @ParameterizedTest
    @CsvSource({
            "kvin, ko@mail.ru, Konstantin, Vinogradov, passP123dssaaa, 18, 1",
            "plotnik, alex@mail.ru, Alexey, Lobanov, passP123dssaaa, 22, 1"
    })
    public void save(String userName, String email, String fName,
                     String lName, String password, int age, int result)
            throws SQLException, InterruptedException {

        UserCreateRequest userCreateRequest = new UserCreateRequest(
                userName, email, fName, lName, password, age);

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Mockito.when(userService.save(userCreateRequest)).thenReturn(result);

        Assertions.assertEquals(result, userService.save(userCreateRequest));
    }

    @Test
    public void findAllTest() throws SQLException, InterruptedException {

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.findAll()).thenReturn(users);


        ArrayList<User> usersResult = userService.findAll();

        Assertions.assertEquals(users.get(0), usersResult.get(0));
        Assertions.assertEquals(users.get(1), usersResult.get(1));
        Assertions.assertEquals(users.get(2), usersResult.get(2));
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "2",
            "3"
    })
    public void findByIdTestPositive(int id) throws SQLException, InterruptedException {

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.findById(id)).thenReturn(users.get(id - 1));

        User user = userService.findById(id);

        Assertions.assertEquals(users.get(id - 1), user);
    }


    @ParameterizedTest
    @CsvSource({
            "16384"
    })
    public void findByIdTestNegative(int id) throws SQLException, InterruptedException {

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.findById(id)).thenReturn(null);

        User user = userService.findById(id);
        Assertions.assertNull(user);
    }


    @ParameterizedTest
    @CsvSource({
            "0, user1",
            "1, user2",
            "2, user3"
    })
    public void findByUsernameTestPositive(final String strIndex, final String username) throws SQLException, InterruptedException {

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        int index = Integer.parseInt(strIndex);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(users.get(index));

        User user = userService.findByUsername(username);

        Assertions.assertEquals(users.get(index), user);
    }

    @ParameterizedTest
    @CsvSource({
            "null, failUser"
    })
    public void findByUsernameTestNegative(final String username) throws SQLException, InterruptedException {

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(null);

        User user = userService.findByUsername(username);
        Assertions.assertNull(user);
    }

    @ParameterizedTest
    @CsvSource({
            "cherepok@mail.ru, true", "nuskov@mail.ru, true", "cherepok@mail.ru, true", "ko@mail.ru, false"
    })
    public void emailExistsTest(String email, String result) throws SQLException, InterruptedException {
        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Mockito.when(userService.emailExist(email)).thenReturn(Boolean.valueOf(result));

        Assertions.assertEquals(userService.emailExist(email), Boolean.valueOf(result));
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void deleteTest(int id) throws SQLException, InterruptedException {

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Mockito.when(userService.delete(id)).thenReturn(1);

        Assertions.assertEquals(
                1, userService.delete(id));
    }



    @ParameterizedTest
    @CsvSource({
            "1, update",
            "2, update",
            "3, update"
    })
    public void updateTest(int id, String update) throws SQLException, InterruptedException {

//        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);

//        UserServiceImpl userService = new UserServiceImpl(userRepository);
        final var userService = Mockito.mock(UserServiceImpl.class);

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(update, update, 26);

        Mockito.when(userService.update(id, userUpdateRequest)).thenReturn(1);

        Assertions.assertEquals(1, userService.update(id, userUpdateRequest));

    }



}

