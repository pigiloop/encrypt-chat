package ru.vinhome.repository;

import ru.vinhome.model.User;
import ru.vinhome.util.ConnectionUtil;

import java.sql.SQLException;

/**
 * Интерфейс UserRepository.
 * В данном интерфейсе описаны методы для реализации CRUD в базе данных,
 * для работы непосредственно с данными типа User.
 *
 */
public interface UserRepository {

    String EXISTS_USER_BY_ID = """
            SELECT EXISTS (select * from public.users where id=?);
            """;

    /**
     * Метод проверяет есть ли id пользователя в таблице users
     *
     * @param id идентификатор пользователя
     * @return если id пользователя есть в базе данных возвращается true иначе возвращается false
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     */
    static boolean userExistByID(int id) throws SQLException {
        try (var connection = ConnectionUtil.getConnection();
             var prepareStatement = connection.prepareStatement(EXISTS_USER_BY_ID);) {
            prepareStatement.setInt(1, id);
            return prepareStatement.execute();
        }
    }


    /**
     * Метод проверяет наличие записи электронной почты в таблице users.
     *
     * @param email электронная почта пользователя типа String
     * @return возвращает true или false в зависимости есть ли указанная электронная почта в таблице или нет.
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    boolean emailExist(String email) throws SQLException;

    /**
     * Метод выводит запись таблицы по имени пользователя.
     *
     * @param userName имя пользователя типа String
     * @return возвращает объект типа User
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    User findByUsername(String userName) throws SQLException;
}
