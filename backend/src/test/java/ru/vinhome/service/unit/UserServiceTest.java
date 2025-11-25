package ru.vinhome.service.unit;

import lombok.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.postgresql.util.PSQLException;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserServiceTest {

    private static ArrayList<User> users = null;

    @Mock
    private JdbcUserRepositoryImpl jdbcUserRepository;

    @InjectMocks
    private UserServiceImpl userService;


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

        UserServiceImpl userService = new UserServiceImpl();

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
        UserServiceImpl userService = new UserServiceImpl();

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
    public void findByIdTest(String id, String hasResult) throws SQLException, InterruptedException {
        UserServiceImpl userService = new UserServiceImpl();

        int index = Integer.parseInt(id);
        User user = userService.findById(Long.parseLong(id));


        if (hasResult.equals("true")) {
            Assertions.assertEquals(users.get(index - 1), user);
        } else {
            Assertions.assertNull(user);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0, user1",
            "1, user2",
            "2, user3",
            "null, failUser"
    })
    public void findByUsernameTest(final String strIndex, final String username) throws SQLException, InterruptedException {
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.findByUsername(username);

        if (strIndex.equals("null")) {
            Assertions.assertNull(user);
        } else {
            int index = Integer.parseInt(strIndex);
            Assertions.assertEquals(users.get(index), user);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "cherepok@mail.ru, true", "nuskov@mail.ru, true", "cherepok@mail.ru, true", "ko@mail.ru, false"
    })
    public void emailExistsTest(String email, String result) throws SQLException, InterruptedException {
        UserServiceImpl userService = new UserServiceImpl();

        Assertions.assertEquals(userService.emailExist(email), Boolean.valueOf(result));
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void deleteTest(String id) throws SQLException, InterruptedException {
        UserServiceImpl userService = new UserServiceImpl();
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
        UserServiceImpl userService = new UserServiceImpl();

        User user = users.get(Integer.parseInt(id) - 1);
        user.setFirstName(update);
        user.setLastName(update);

        Assertions.assertEquals(1, userService.update(Long.parseLong(id), user));
        Assertions.assertEquals(
                users.get(Integer.parseInt(id) - 1),
                userService.findById(Long.parseLong(id))
        );
    }

}

