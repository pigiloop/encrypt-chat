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

import java.io.IOException;
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
    void createTable() throws SQLException, InterruptedException, URISyntaxException, IOException {

        PostgresTestContainer.initSQL(Paths.get(ContainerTest.class.getClassLoader().getResource("init_db.sql").toURI()));

        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();

        messages = new ArrayList<>();
        messages.addAll(jdbcMessageRepository.findAll());
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
    public void insertDataTest(String id, String strSender, String strRecipient, String text, int result)
            throws SQLException, InterruptedException, URISyntaxException {

        JdbcUserRepositoryImpl jdbcUserRep = new JdbcUserRepositoryImpl();

        Message message = Message.createMessage(
                Long.valueOf(id),
                jdbcUserRep.findById(Long.valueOf(strSender)),
                jdbcUserRep.findById(Long.valueOf(strRecipient)),
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
    public void findByIdTest(String id, String isTrue) throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();

        int index = Integer.valueOf(id);
        Message message = jdbcMessageRepository.findById(Long.valueOf(index));

        if (isTrue.equals("true")) {
            Assertions.assertEquals(messages.get(index - 1), message);
        } else {
            Assertions.assertNull(message);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3"
    })
    public void deleteTest(String id) throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
        Assertions.assertEquals(
                1, jdbcMessageRepository.delete(Long.valueOf(id)));
    }


    @ParameterizedTest
    @CsvSource({
            "1, update",
            "2, update"
    })
    public void updateTest(String id, String update) throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();

        Message message = jdbcMessageRepository.findById(Long.valueOf(id));
        message.setMessage(update);
        messages.get(Integer.parseInt(id) - 1).setMessage(update);

        Assertions.assertEquals(1, jdbcMessageRepository.update(Long.parseLong(id), message));
        Assertions.assertEquals(
                messages.get(Integer.parseInt(id) - 1),
                jdbcMessageRepository.findById(Long.parseLong(id))
        );

    }
}
