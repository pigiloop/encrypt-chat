package ru.vinhome.service.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.vinhome.model.Message;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.service.MessageServiceImpl;
import ru.vinhome.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class MessageServiceTest {
    private static ArrayList<Message> messages = null;
    private static ArrayList<User> users = null;

    @BeforeEach
    void createTable() throws SQLException, InterruptedException {

        createAndFillUserTable();

        MessageServiceImpl messageService = new MessageServiceImpl();
        messageService.createTable();

        JdbcUserRepositoryImpl jdbcUserRep = new JdbcUserRepositoryImpl();

        messages = new ArrayList<>();

        messages.add(Message.createMessage(1L, jdbcUserRep.findById(1L), jdbcUserRep.findById(2L),
                "Привет, друг! Как твои делишки?", null));
        messages.add(Message.createMessage(2L, jdbcUserRep.findById(2L), jdbcUserRep.findById(1L),
                "Привет, отлично! Как ты сам?", null));
        messages.add(Message.createMessage(3L, jdbcUserRep.findById(1L), jdbcUserRep.findById(2L),
                "Да так развлекаюсь, Паша интересные задачки подкинул, вот сижу тут развлекаюсь!", null));
        messages.add(Message.createMessage(4L, jdbcUserRep.findById(2L), jdbcUserRep.findById(1L),
                "Понял тебя, держись там.", null));
        messages.add(Message.createMessage(5L, jdbcUserRep.findById(3L), jdbcUserRep.findById(1L),
                "Привет, а вы про меня совсем забыли?", null));

        for (Message message : messages) {
            messageService.save(message);
        }

        messages.clear();
        messages.addAll(messageService.findAll());
    }


    private static void createAndFillUserTable() throws SQLException, InterruptedException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createTable();

        users = new ArrayList<>();

        users.add(new User(1L, "user1", "klepeshkin@mail.ru", "Konstantin",
                "Lepeshkin", "qwerty", 18));

        users.add(new User(2L, "user2", "nuskov@mail.ru", "Nikita",
                "Uskov", "qwerty", 25));

        users.add(new User(3L, "user3", "cherepok@mail.ru", "Oleg",
                "Cherpanov", "qwerty", 23));

        for (User user : users) {
            userService.save(user);
        }

    }


    @AfterEach
    void dropTable() throws SQLException, InterruptedException {
        MessageServiceImpl messageService = new MessageServiceImpl();
        messageService.dropTable();


        UserServiceImpl userService = new UserServiceImpl();
        userService.dropTable();
    }

    @ParameterizedTest
    @CsvSource({
            "7, 3, 2, Сегодня идём есть пиццу, 1",
            "8, 2, 3, Отлично тогда ты платишь, 1",
            "9, 2, 3, Договорились но тогда мы идём без тебя ))), 1"
    })
    public void insertDataTest(String id, String strSender, String strRecipient, String text, int result)
            throws SQLException, InterruptedException {

        UserServiceImpl userService = new UserServiceImpl();

        Message message = Message.createMessage(
                Long.valueOf(id),
                userService.findById(Long.valueOf(strSender)),
                userService.findById(Long.valueOf(strRecipient)),
                text,
                null);

        MessageServiceImpl messageService = new MessageServiceImpl();
        Assertions.assertEquals(result, messageService.save(message));

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
    public void findByIdTest(String id, String isTrue) throws SQLException, InterruptedException {
        MessageServiceImpl messageService = new MessageServiceImpl();

        int index = Integer.parseInt(id);
        Message message = messageService.findById(Long.parseLong(id));

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
        MessageServiceImpl messageService = new MessageServiceImpl();
        Assertions.assertEquals(
                1, messageService.delete(Long.valueOf(id)));
    }


    @ParameterizedTest
    @CsvSource({
            "1, update",
            "2, update"
    })
    public void updateTest(String id, String update) throws SQLException, InterruptedException {
        MessageServiceImpl messageService = new MessageServiceImpl();

        Message message = messageService.findById(Long.valueOf(id));
        message.setMessage(update);
        messages.get(Integer.parseInt(id) - 1).setMessage(update);

        Assertions.assertEquals(1, messageService.update(Long.parseLong(id), message));
        Assertions.assertEquals(
                messages.get(Integer.parseInt(id) - 1),
                messageService.findById(Long.parseLong(id))
        );

    }

}
