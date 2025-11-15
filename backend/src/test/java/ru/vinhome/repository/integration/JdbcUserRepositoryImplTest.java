package ru.vinhome.repository.integration;

import lombok.NonNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.postgresql.util.PSQLException;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcMessageRepositoryImpl;
import ru.vinhome.repository.JdbcUserRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JdbcUserRepositoryImplTest {

    private static ArrayList<User> users = null;

    @BeforeAll
    static void createTable() throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();
        jdbcUserRepository.createTable();

        users = new ArrayList<>();

        users.add(new User(1L, "user1", "klepeshkin@mail.ru", "Konstantin",
                "Lepeshkin", "qwerty", 18));

        users.add(new User(2L, "user2", "nuskov@mail.ru", "Nikita",
                "Uskov", "qwerty", 25));

        users.add(new User(3L, "user3", "cherepok@mail.ru", "Oleg",
                "Cherpanov", "qwerty", 23));

        for (User user : users) {
            jdbcUserRepository.save(user);
        }

    }


    @AfterAll
    static void dropTable() throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
        jdbcMessageRepository.dropTable();


        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();
        jdbcUserRepository.dropTable();
    }

//
//     Здесь надо как-то добавить обработчик неудачных событий
//             "5, sudak, ko@mail.ru, Pavel,  Sudak, 19, 0, true",

    @ParameterizedTest
    @CsvSource({
            "4, kvin, ko@mail.ru, Konstantin, Vinogradov, 18, 1, false",
            "6, plotnik, alex@mail.ru, Alexey, Lobanov, 22, 1, false"
    })
    public void insertData(String id, @NonNull String userName, String email, String fName,
                           String lName, String age, int result, Boolean isException)
            throws SQLException, InterruptedException {

        User user = User.builder()
                .id(Long.valueOf(id))
                .userName(userName)
                .email(email)
                .firstName(fName)
                .lastName(lName)
                .age(Integer.valueOf(age))
                .build();

        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        if (isException) {
            Exception exception = assertThrows(PSQLException.class, () -> jdbcUserRepository.save(user));
            Assertions.assertEquals("ERROR: duplicate key value violates unique constraint \"users_email_key\"\n" +
                    "  Detail: Key (email)=(ko@mail.ru) already exists.", exception.getMessage());
        } else {
            Assertions.assertEquals(result, jdbcUserRepository.save(user));
        }
    }

    @Test
    public void findAll() throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        ArrayList<User> usersResult =  jdbcUserRepository.findAll();

        Assertions.assertEquals(users.get(0), usersResult.get(0));
        Assertions.assertEquals(users.get(1 ), usersResult.get(1));
        Assertions.assertEquals(users.get(2), usersResult.get(2));
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void findById(String id) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        int index = Integer.valueOf(id);
        User user = jdbcUserRepository.findById(Long.valueOf(index));

        Assertions.assertEquals(users.get(index-1), user);
    }

    @ParameterizedTest
    @CsvSource({
            "0, user1", "1, user2", "2, user3"
    })
    public void findByUsername(final String strIndex, final String username) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        User user = jdbcUserRepository.findByUsername(username);
        int index = Integer.valueOf(strIndex);

        Assertions.assertEquals(users.get(index), user);
    }
}
