package ru.vinhome.repository.integration;

import lombok.NonNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.vinhome.controller.integration.ContainerTest;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcMessageRepositoryImpl;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.util.ConnectionUtil;
import ru.vinhome.util.PostgresTestContainer;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;


public class JdbcUserRepositoryImplTest {

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
    void createTable() throws SQLException, InterruptedException, URISyntaxException {
        PostgresTestContainer.initSQL(Paths.get(ContainerTest.class.getClassLoader().getResource("init_db.sql").toURI()));

        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        users = jdbcUserRepository.findAll();

 /*
        jdbcUserRepository.createTable();


        users.add(new User(1L, "user1", "klepeshkin@mail.ru", "Konstantin",
                "Lepeshkin", "qwerty", 18));

        users.add(new User(2L, "user2", "nuskov@mail.ru", "Nikita",
                "Uskov", "qwerty", 25));

        users.add(new User(3L, "user3", "cherepok@mail.ru", "Oleg",
                "Cherpanov", "qwerty", 23));

        for (User user : users) {
            jdbcUserRepository.save(user);
        }
*/
    }


    @AfterEach
    void dropTable() throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
        jdbcMessageRepository.dropTable();

        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();
        jdbcUserRepository.dropTable();
    }

    @ParameterizedTest
    @CsvSource({
            "4, kvin, ko@mail.ru, Konstantin, Vinogradov, 18, 1",
            "6, plotnik, alex@mail.ru, Alexey, Lobanov, 22, 1"
    })
    public void insertDataTest(int id, @NonNull String userName, String email, String fName,
                               String lName, String age, int result)
            throws SQLException, InterruptedException {

        User user = User.builder()
                .id(id)
                .userName(userName)
                .email(email)
                .firstName(fName)
                .lastName(lName)
                .age(Integer.parseInt(age))
                .build();

        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        Assertions.assertEquals(result, jdbcUserRepository.save(user));
    }

    @Test
    public void findAllTest() throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        ArrayList<User> usersResult = jdbcUserRepository.findAll();

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
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        User user = jdbcUserRepository.findById(id);

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
            "2, user3",
            "null, failUser"
    })
    public void findByUsernameTest(final String strIndex, final String username) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        User user = jdbcUserRepository.findByUsername(username);

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
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        Assertions.assertEquals(jdbcUserRepository.emailExist(email), Boolean.valueOf(result));
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void deleteTest(int id) throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
        jdbcMessageRepository.dropTable();

        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();
        Assertions.assertEquals(
                1, jdbcUserRepository.delete(id));
    }

    @ParameterizedTest
    @CsvSource({
            "1, update",
            "2, update",
            "3, update"
    })
    public void updateTest(int id, String update) throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        User user = users.get(id - 1);
        user.setFirstName(update);
        user.setLastName(update);

        Assertions.assertEquals(1, jdbcUserRepository.update(id, user));
        Assertions.assertEquals(
                users.get(id - 1),
                jdbcUserRepository.findById(id)
        );
    }

}
