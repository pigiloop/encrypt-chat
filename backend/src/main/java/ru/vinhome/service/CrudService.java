package ru.vinhome.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
     * *
     */
    List<T> findAll() throws SQLException;

    /**
     * Метод выводит запись таблицы по её уникальному идентификатору.
     * @param id идентификатор типа I
     * @return возвращает объект типа T
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     * *
     */
    Optional<T> findById(I id) throws SQLException;

    /**
     * Метод сохраняет запись в таблице.
     * @param obj объект типа S
     * @return возвращает количество изменённых записей
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     **
     */
    int save(S obj) throws SQLException;

    /**
     * Метод изменяет запись в таблице.
     * @param id идентификатор типа I
     * @param obj объект типа T
     * @return возвращает количество изменённых записей
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     **
     */
    int update(I id, Q obj) throws SQLException;

    /**
     * Метод удаляет запись из таблицы.
     * @param id идентификатор типа I
     * @return возвращает количество изменённых записей
     * @exception SQLException выкидывает исключения в случае если база данных недоступна
     * *
     */
    int delete(I id) throws SQLException;
}
