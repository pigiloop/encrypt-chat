package ru.vinhome.service;

import java.sql.SQLException;

/**
 * Интерфейс ITableManagement.
 * В данном интерфейсе описаны методы для реализации сервиса CRUD в базе данных,
 * для работы непосредственно с данными типа User.
 *
 */
public interface TableManagement {

    /**
     * Метод создаёт таблицу.
     * *
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     */
    void createTable() throws SQLException;

    /**
     * Метод удаляет таблицу.
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     * *
     */
    void dropTable() throws SQLException;
}
