package ru.vinhome.service.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.vinhome.controller.dto.MessageCreateRequest;
import ru.vinhome.controller.dto.MessageUpdateRequest;
import ru.vinhome.controller.integration.ContainerTest;
import ru.vinhome.model.Message;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.service.MessageServiceImpl;
import ru.vinhome.service.UserServiceImpl;
import ru.vinhome.util.ConnectionUtil;
import ru.vinhome.util.PostgresTestContainer;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageServiceTestIntegration {
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

        PostgresTestContainer.initSQL(Paths.get(
                ContainerTest.class.getClassLoader().getResource("init_db.sql").toURI()));

        MessageServiceImpl messageService = new MessageServiceImpl();
        UserServiceImpl userService = new UserServiceImpl(new JdbcUserRepositoryImpl());

        messages = messageService.findAll();
        users = userService.findAll();
    }

    @AfterEach
    void dropTable() {
        users.clear();
        messages.clear();
    }

    @ParameterizedTest
    @CsvSource({
            "3, 2, Сегодня идём есть пиццу, 1",
            "2, 3, Отлично тогда ты платишь, 1",
            "2, 3, Договорились но тогда мы идём без тебя ))), 1"
    })
    public void insertDataTest(int senderId, int recipientId, String text, int result)
            throws SQLException, InterruptedException {

        MessageServiceImpl messageService = new MessageServiceImpl();
        MessageCreateRequest messageCreateRequest =
                new MessageCreateRequest(
                        users.get(senderId).getId(),
                        users.get(recipientId).getId(),
                        text);

        Assertions.assertEquals(result, messageService.save(messageCreateRequest));

    }

    @Test
    public void findAllTest() throws SQLException, InterruptedException {
        MessageServiceImpl messageService = new MessageServiceImpl();

        ArrayList<Message> messageArrayList = messageService.findAll();


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
        MessageServiceImpl messageService = new MessageServiceImpl();

        Message message = messageService.findById(id).get();

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
        MessageServiceImpl messageService = new MessageServiceImpl();
        Assertions.assertEquals(
                1, messageService.delete(id));
    }


    @ParameterizedTest
    @CsvSource({
            "1, update",
            "2, update"
    })
    public void updateTest(int id, String update) throws SQLException, InterruptedException {
        MessageServiceImpl messageService = new MessageServiceImpl();

        MessageUpdateRequest messageUpdateRequest = new MessageUpdateRequest(
                update
        );

        messages.get(id - 1).setMessage(update);
        Assertions.assertEquals(1, messageService.update(id, messageUpdateRequest));
        Assertions.assertEquals(
                messages.get(id - 1),
                messageService.findById(id)
        );

    }

}
