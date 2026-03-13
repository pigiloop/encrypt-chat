package ru.vinhome.repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс BaseRepository.
 * В данном интерфейсе описаны методы для реализации CRUD в базе данных.
 *
 * @param <T> bla bla bla
 * @param <I> blu blu blu
 */
public interface BaseRepository<T, I> {
    /**
     * Метод выводит все записи таблицы.
     *
     * @return возвращает список объектов типа T
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     */
    List<T> findAll() throws SQLException;

    /**
     * Метод выводит запись таблицы по её уникальному идентификатору.
     *
     * @param id идентификатор типа I
     * @return возвращает объект типа T
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     *                              *
     */
    T findById(I id) throws SQLException;

    /**
     * Метод сохраняет запись в таблице.
     *
     * @param obj объект типа T
     * @return возвращает количество изменённых записей
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     *                              *
     */
    int save(T obj) throws SQLException;

    /**
     * Метод изменяет запись в таблице.
     *
     * @param id  идентификатор типа I
     * @param obj объект типа T
     * @return возвращает количество изменённых записей
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     *                              *
     */
    int update(I id, T obj) throws SQLException;

    /**
     * Метод удаляет запись из таблицы.
     *
     * @param id идентификатор типа I
     * @return возвращает количество изменённых записей
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     *                              *
     */
    int delete(I id) throws SQLException;

    /**
     * Метод создаёт таблицу.
     *
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     *                              *
     */
    void createTable() throws SQLException;

    /**
     * Метод удаляет таблицу.
     *
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     *                              *
     */
    void dropTable() throws SQLException;
}
