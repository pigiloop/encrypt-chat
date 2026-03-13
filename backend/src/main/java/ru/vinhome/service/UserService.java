package ru.vinhome.service;

import ru.vinhome.controller.dto.UserCreateRequest;
import ru.vinhome.controller.dto.UserUpdateRequest;
import ru.vinhome.model.User;

import java.sql.SQLException;

public interface UserService extends CrudService<User, UserCreateRequest, UserUpdateRequest, Integer>, TableManagement {

    /**
     * Метод проверяет наличие записи электронной почты в таблице users.
     *
     * @param email электронная почта пользователя типа String
     * @return возвращает true или false в зависимости есть ли указанная электронная почта в таблице или нет.
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *
     */
    boolean emailExist(String email) throws SQLException;

    /**
     * Метод выводит запись таблицы по имени пользователя.
     *
     * @param userName имя пользователя типа String
     * @return возвращает объект типа User
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see User
     *
     */
    User findByUsername(String userName) throws SQLException;

}
