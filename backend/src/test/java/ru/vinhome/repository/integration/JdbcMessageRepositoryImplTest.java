package ru.vinhome.repository.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.vinhome.model.Message;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcMessageRepositoryImpl;
import ru.vinhome.repository.JdbcUserRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcMessageRepositoryImplTest {

    private static ArrayList<Message> messages = null;
    private static ArrayList<User> users = null;

    @BeforeAll
    static void сreateTable() throws SQLException, InterruptedException {

        createAndFillUserTable();

        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
        jdbcMessageRepository.createTable();

        JdbcUserRepositoryImpl jdbcUserRep = new JdbcUserRepositoryImpl();

        messages = new ArrayList<>();

        messages.add(Message.createMessage(1L, jdbcUserRep.findById( 1L),jdbcUserRep.findById( 2L),
                "Привет, друг! Как твои делишки?"));
        messages.add(Message.createMessage(2L,jdbcUserRep.findById( 2L),jdbcUserRep.findById( 1L),
                "Привет, отлично! Как ты сам?"));
        messages.add(Message.createMessage(3L, jdbcUserRep.findById( 1L),jdbcUserRep.findById( 2L),
                "Да так развлекаюсь, Паша тут интересные задачки подкинул, вот сижу тут развлекаюсь!"));
        messages.add(Message.createMessage(4L, jdbcUserRep.findById( 2L),jdbcUserRep.findById( 1L),
                "Понял тебя, держись там."));
        messages.add(Message.createMessage(5L, jdbcUserRep.findById( 3L),jdbcUserRep.findById( 1L),
                "Привет, а вы про меня совсем забыли?"));

        for (Message message : messages) {
            jdbcMessageRepository.save(message);
        }
    }


private static void createAndFillUserTable() throws SQLException, InterruptedException {
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

@ParameterizedTest
@CsvSource({
        "7, 3, 2, Сегодня идём есть пиццу, 1",
        "8, 2, 3, Отлично тогда ты платишь, 1",
        "9, 2, 3, Договорились но тогда мы идём без тебя ))), 1"
})
public void insertData(String id, String strSender, String strRecipient, String text, int result)
        throws SQLException, InterruptedException {

    JdbcUserRepositoryImpl jdbcUserRep = new JdbcUserRepositoryImpl();

    Message message = Message.createMessage(
            Long.valueOf(id),
            jdbcUserRep.findById(Long.valueOf(strSender)),
            jdbcUserRep.findById(Long.valueOf(strRecipient)),
            text);

    JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
    Assertions.assertEquals(result, jdbcMessageRepository.save(message));

}

@Test
public void findAll() throws SQLException, InterruptedException {
    JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();

    ArrayList<Message> messageArrayList = jdbcMessageRepository.findAll();

    Assertions.assertEquals(messages.get(0), messageArrayList.get(0));
    Assertions.assertEquals(messages.get(1), messageArrayList.get(1));
    Assertions.assertEquals(messages.get(2), messageArrayList.get(2));
    Assertions.assertEquals(messages.get(2), messageArrayList.get(2));
}


@ParameterizedTest
@CsvSource({
        "1", "2", "3"
})
public void findById(String id) throws SQLException, InterruptedException {
    JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();

    int index = Integer.valueOf(id);
    Message message = jdbcMessageRepository.findById(Long.valueOf(index));

    Assertions.assertEquals(messages.get(index - 1), message);
}

}
