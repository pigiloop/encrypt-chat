package ru.vinhome.service;

import ru.vinhome.model.User;

import java.sql.SQLException;

/**
 * Интерфейс IUserService.
 * В данном интерфейсе описаны методы для реализации сервиса CRUD в базе данных,
 * для работы непосредственно с данными типа User.
 *
 */
public interface IUserService {
    /**
     * Метод проверяет наличие записи электронной почты в таблице users.
     * @param email электронная почта пользователя типа String
     * @return возвращает true или false в зависимости есть ли указанная электронная почта в таблице или нет.
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * *
     */
    boolean emailExist(String email) throws SQLException, InterruptedException;

    /**
     * Метод выводит запись таблицы по имени пользователя.
     * @param userName имя пользователя типа String
     * @return возвращает объект типа User
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    User findByUsername(String userName) throws SQLException, InterruptedException;
}
