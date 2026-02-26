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
     * @throws InterruptedException выкидывает исключение если все ресурсы заняты
     */
    List<T> findAll() throws SQLException, InterruptedException;

    /**
     * Метод выводит запись таблицы по её уникальному идентификатору.
     *
     * @param id идентификатор типа I
     * @return возвращает объект типа T
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     * @throws InterruptedException выкидывает исключение если все ресурсы заняты
     *                              *
     */
    T findById(I id) throws SQLException, InterruptedException;

    /**
     * Метод сохраняет запись в таблице.
     *
     * @param obj объект типа T
     * @return возвращает количество изменённых записей
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     * @throws InterruptedException выкидывает исключение если все ресурсы заняты
     *                              *
     */
    int save(T obj) throws SQLException, InterruptedException;

    /**
     * Метод изменяет запись в таблице.
     *
     * @param id  идентификатор типа I
     * @param obj объект типа T
     * @return возвращает количество изменённых записей
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     * @throws InterruptedException выкидывает исключение если все ресурсы заняты
     *                              *
     */
    int update(I id, T obj) throws SQLException, InterruptedException;

    /**
     * Метод удаляет запись из таблицы.
     *
     * @param id идентификатор типа I
     * @return возвращает количество изменённых записей
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     * @throws InterruptedException выкидывает исключение если все ресурсы заняты
     *                              *
     */
    int delete(I id) throws SQLException, InterruptedException;

    /**
     * Метод создаёт таблицу.
     *
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     * @throws InterruptedException выкидывает исключение если все ресурсы заняты
     *                              *
     */
    void createTable() throws SQLException, InterruptedException;

    /**
     * Метод удаляет таблицу.
     *
     * @throws SQLException         выкидывает исключения в случае если база данных недоступна
     * @throws InterruptedException выкидывает исключение если все ресурсы заняты
     *                              *
     */
    void dropTable() throws SQLException, InterruptedException;
}
