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
public interface CrudService<T, S, Q, I> {
    /**
     * Метод выводит все записи таблицы.
     * @return возвращает список объектов типа T
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     * @exception InterruptedException выкидывает исключение если все ресурсы заняты
     * *
     */
    List<T> findAll() throws SQLException, InterruptedException;

    /**
     * Метод выводит запись таблицы по её уникальному идентификатору.
     * @param id идентификатор типа I
     * @return возвращает объект типа T
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     * @exception InterruptedException выкидывает исключение если все ресурсы заняты
     * *
     */
    T findById(I id) throws SQLException, InterruptedException;

    /**
     * Метод сохраняет запись в таблице.
     * @param obj объект типа S
     * @return возвращает количество изменённых записей
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     * @exception InterruptedException выкидывает исключение если все ресурсы заняты
     **
     */
    int save(S obj) throws SQLException, InterruptedException;

    /**
     * Метод изменяет запись в таблице.
     * @param id идентификатор типа I
     * @param obj объект типа T
     * @return возвращает количество изменённых записей
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     * @exception InterruptedException выкидывает исключение если все ресурсы заняты
     **
     */
    int update(I id, Q obj) throws SQLException, InterruptedException;

    /**
     * Метод удаляет запись из таблицы.
     * @param id идентификатор типа I
     * @return возвращает количество изменённых записей
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     * @exception InterruptedException выкидывает исключение если все ресурсы заняты
     * *
     */
    int delete(I id) throws SQLException, InterruptedException;
}
