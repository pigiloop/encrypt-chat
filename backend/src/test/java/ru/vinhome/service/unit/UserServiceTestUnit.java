package ru.vinhome.service.unit;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.postgresql.util.PSQLException;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserServiceTestUnit {

    private static ArrayList<User> users = null;

    @BeforeAll
    public static void fillUsersArrayList() {
        users = new ArrayList<>();

        users.add(new User(1L, "user1", "klepeshkin@mail.ru", "Konstantin",
                "Lepeshkin", "qwerty", 18));

        users.add(new User(2L, "user2", "nuskov@mail.ru", "Nikita",
                "Uskov", "qwerty", 25));

        users.add(new User(3L, "user3", "cherepok@mail.ru", "Oleg",
                "Cherpanov", "qwerty", 23));
    }

    @ParameterizedTest
    @CsvSource({
            "4, kvin, ko@mail.ru, Konstantin, Vinogradov, 18, 1, false",
            "6, plotnik, alex@mail.ru, Alexey, Lobanov, 22, 1, false"
    })
    public void save(String id, @NonNull String userName, String email, String fName,
                     String lName, String age, int result, Boolean isException) throws SQLException, InterruptedException {

        User user = User.builder()
                .id(Long.valueOf(id))
                .userName(userName)
                .email(email)
                .firstName(fName)
                .lastName(lName)
                .age(Integer.parseInt(age))
                .build();

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.save(user)).thenReturn(result);

        if (isException) {
            Exception exception = assertThrows(PSQLException.class, () -> userService.save(user));
            Assertions.assertEquals("ERROR: duplicate key value violates unique constraint \"users_email_key\"\n"
                    + "  Detail: Key (email)=(ko@mail.ru) already exists.", exception.getMessage());
        } else {
            Assertions.assertEquals(result, userService.save(user));
        }
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
            "1, true",
            "2, true",
            "3, true"
    })
    public void findByIdTestPositive(String id, String hasResult) throws SQLException, InterruptedException {

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        int index = Integer.parseInt(id);
        Mockito.when(userRepository.findById(Long.parseLong(id))).thenReturn(users.get(index - 1));

        User user = userService.findById(Long.parseLong(id));

        Assertions.assertEquals(users.get(index - 1), user);
    }


    @ParameterizedTest
    @CsvSource({
            "16384, false"
    })
    public void findByIdTestNegative(String id, String hasResult) throws SQLException, InterruptedException {

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        int index = Integer.parseInt(id);
        Mockito.when(userRepository.findById(Long.parseLong(id))).thenReturn(null);

        User user = userService.findById(Long.parseLong(id));
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
    public void findByUsernameTestNegative(final String strIndex, final String username) throws SQLException, InterruptedException {

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
    public void deleteTest(String id) throws SQLException, InterruptedException {

        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        Mockito.when(userService.delete(Long.parseLong(id))).thenReturn(1);

        Assertions.assertEquals(
                1, userService.delete(Long.valueOf(id)));
    }

    @ParameterizedTest
    @CsvSource({
            "1, update",
            "2, update",
            "3, update"
    })
    public void updateTest(String id, String update) throws SQLException, InterruptedException {
        final var userRepository = Mockito.mock(JdbcUserRepositoryImpl.class);
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        User user = users.get(Integer.parseInt(id) - 1);
        user.setFirstName(update);
        user.setLastName(update);

        Mockito.when(userService.update(Long.parseLong(id), user)).thenReturn(1);
        Mockito.when(userService.findById(Long.parseLong(id))).thenReturn(user);

        Assertions.assertEquals(1, userService.update(Long.parseLong(id), user));
        Assertions.assertEquals(
                users.get(Integer.parseInt(id) - 1),
                userService.findById(Long.parseLong(id))
        );
    }

}

