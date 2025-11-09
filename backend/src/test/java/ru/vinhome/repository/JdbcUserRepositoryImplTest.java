package ru.vinhome.repository;

import lombok.NonNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.postgresql.util.PSQLException;
import ru.vinhome.model.User;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JdbcUserRepositoryImplTest {

    @BeforeAll
    static void CreateGameTable() throws SQLException, InterruptedException {
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();
        jdbcUserRepository.createTable();
    }

    @AfterAll
    static void DropTable() throws SQLException, InterruptedException {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
        jdbcMessageRepository.dropTable();


        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();
        jdbcUserRepository.dropTable();
    }

    @ParameterizedTest
    @CsvSource({
            "1, kvin, Konstantin, Vinogradov, ko@mail.ru, 18, 1, false",
            "2, sudak, Pavel,  Sudak, ko@mail.ru, 19, 0, true",
            "3, plotnik, Alexey, Lobanov, alex@mail.ru, 22, 1, false"
    })
    public void InsertData(String id, @NonNull String userName, String fName,
                           String lName, String email, String age, int result, Boolean isException)
            throws SQLException, InterruptedException {

        User user = User.builder()
                .id(Long.valueOf(id))
                .userName(userName)
                .firstName(fName)
                .lastName(lName)
                .email(email)
                .age(Integer.valueOf(age))
                .build();

        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        if (isException) {
            Exception exception = assertThrows(PSQLException.class, () -> jdbcUserRepository.save(user));
            Assertions.assertEquals("ERROR: duplicate key value violates unique constraint \"users_username_key\"\n" +
                    "  Detail: Key (username)=(ko@mail.ru) already exists.", exception.getMessage());
        }
        else {
            Assertions.assertEquals(result, jdbcUserRepository.save(user));
        }
    }

        /*        User user = User.builder()
                .id(Long.valueOf("1L"))
                .userName("kvin")
                .firstName("Konstantin")
                .lastName("Vinogradov")
                .email("ko@mail.ru")
                .age(18)
                .build();*/

}
