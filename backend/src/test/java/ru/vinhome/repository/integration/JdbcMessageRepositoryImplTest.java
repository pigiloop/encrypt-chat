package ru.vinhome.repository.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.vinhome.controller.integration.ContainerTest;
import ru.vinhome.model.Message;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcMessageRepositoryImpl;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.util.ConnectionUtil;
import ru.vinhome.util.PostgresTestContainer;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;


public class JdbcMessageRepositoryImplTest {

    private static ArrayList<Message> messages = null;
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

        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();

        messages = jdbcMessageRepository.findAll();
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
            "7, 3, 2, Сегодня идём есть пиццу, 1",
            "8, 2, 3, Отлично тогда ты платишь, 1",
            "9, 2, 3, Договорились но тогда мы идём без тебя ))), 1"
    })
    public void insertDataTest(int id, int senderId, int recipientId, String text, int result)
            throws SQLException, InterruptedException {

        Message message = Message.createMessage(
                id,
                senderId,
                recipientId,
                text,
                null);

        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
        Assertions.assertEquals(result, jdbcMessageRepository.save(message));

    }

    @Test
    public void findAllTest() throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();

        ArrayList<Message> messageArrayList = jdbcMessageRepository.findAll();

        Assertions.assertEquals(messages.get(0), messageArrayList.get(0));
        Assertions.assertEquals(messages.get(1), messageArrayList.get(1));
        Assertions.assertEquals(messages.get(2), messageArrayList.get(2));
        Assertions.assertEquals(messages.get(2), messageArrayList.get(2));
    }


    @ParameterizedTest
    @CsvSource({
            "1, true",
            "2, true",
            "3, true",
            "40000, false"
    })
    public void findByIdTest(int id, String isTrue) throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();

        Message message = jdbcMessageRepository.findById(id);

        if (isTrue.equals("true")) {
            Assertions.assertEquals(messages.get(id - 1), message);
        } else {
            Assertions.assertNull(message);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void deleteTest(int id) throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
        Assertions.assertEquals(
                1, jdbcMessageRepository.delete(id));
    }


    @ParameterizedTest
    @CsvSource({
            "1, update",
            "2, update"
    })
    public void updateTest(int id, String update) throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();

        Message message = jdbcMessageRepository.findById(id);
        message.setMessage(update);
        messages.get(id - 1).setMessage(update);

        Assertions.assertEquals(1, jdbcMessageRepository.update(id, message));
        Assertions.assertEquals(
                messages.get(id - 1),
                jdbcMessageRepository.findById(id)
        );

    }
}
