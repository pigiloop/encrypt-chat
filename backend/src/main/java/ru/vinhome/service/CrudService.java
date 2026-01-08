package ru.vinhome.service;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс BaseRepository.
 * В данном интерфейсе описаны методы для реализации сервиса CRUD.
 *
 * @param <T> bla bla bla
 * @param <I> hello
 *
 */
public interface CrudService<T, I> {
    /**
     * Метод выводит все записи таблицы.
     * @return возвращает список объектов типа T
     * *
     */
    List<T> findAll() throws SQLException, InterruptedException;

    /**
     * Метод выводит запись таблицы по её уникальному идентификатору.
     * @param id идентификатор типа I
     * @return возвращает объект типа T
     * *
     */
    T findById(I id) throws SQLException, InterruptedException;

    /**
     * Метод сохраняет запись в таблице.
     * @param obj объект типа T
     * @return возвращает количество изменённых записей
     **
     */
    int save(T obj) throws SQLException, InterruptedException;

    /**
     * Метод изменяет запись в таблице.
     * @param id идентификатор типа I
     * @param obj объект типа T
     * @return возвращает количество изменённых записей
     **
     */
    int update(I id, T obj) throws SQLException, InterruptedException;

    /**
     * Метод удаляет запись из таблицы.
     * @param id идентификатор типа I
     * @return возвращает количество изменённых записей
     * *
     */
    int delete(I id) throws SQLException, InterruptedException;
}
