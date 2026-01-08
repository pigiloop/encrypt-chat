package ru.vinhome.service;

import ru.vinhome.model.Message;
import ru.vinhome.repository.BaseRepository;
import ru.vinhome.repository.JdbcMessageRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс MessageServiceImpl реализующий интерфейсы CrudService и TableManagement.
 * В данном классе реализованы методы сервис слоя Message, для манипуляций данными типа Message
 *
 * @see CrudService
 * @see Message
 */
public class MessageServiceImpl implements CrudService<Message, Long>, TableManagement {


    private final JdbcMessageRepositoryImpl jdbcMessageRepository;

    /**
     * Конструктор класса JdbcMessageServiceImpl принимающий в качестве параметра экземпляр класса
     * JdbcMessageRepository
     *
     * @param jdbcMessageRepository параметр типа JdbcMessageRepositoryImpl
     * @see JdbcMessageRepositoryImpl
     * @see Message
     */
    public MessageServiceImpl(JdbcMessageRepositoryImpl jdbcMessageRepository) {
        this.jdbcMessageRepository = jdbcMessageRepository;
    }

    /**
     * Конструктор класса JdbcMessageServiceImpl без параметров
     *
     * @see Message
     */
    public MessageServiceImpl() {
        this.jdbcMessageRepository = new JdbcMessageRepositoryImpl();
    }

    /**
     * Метод выводит все записи таблицы message.
     *
     * @return возвращает список объектов типа Message
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *
     */
    @Override
    public ArrayList<Message> findAll() throws SQLException, InterruptedException {
        return jdbcMessageRepository.findAll();
    }

    /**
     * Метод выводит запись таблицы message по её уникальному идентификатору.     *
     * @param id идентификатор типа Long
     * @return возвращает объект типа Message
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see Message
     * @see Connection
     * *
     */
    @Override
    public Message findById(Long id) throws SQLException, InterruptedException {
        return jdbcMessageRepository.findById(id);
    }

    /**
     * Метод сохраняет запись в таблице message.
     *
     * @param obj объект типа Message
     * @return возвращает количество изменённых записей в таблице message
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see Message
     *
     */
    @Override
    public int save(Message obj) throws SQLException, InterruptedException {
        return jdbcMessageRepository.save(obj);
    }

    /**
     * Метод изменяет запись в таблице message.
     *
     * @param id  идентификатор типа Long
     * @param obj объект типа Message
     * @return возвращает количество изменённых записей
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see Message
     * *
     */
    @Override
    public int update(Long id, Message obj) throws SQLException, InterruptedException {
        return jdbcMessageRepository.update(id, obj);
    }

    /**
     * Метод удаляет запись из таблицы message.
     *
     * @param id идентификатор типа Long
     * @return возвращает количество изменённых записей
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public int delete(Long id) throws SQLException, InterruptedException {
        return jdbcMessageRepository.delete(id);
    }

    /**
     * Метод создаёт таблицу message.
     *
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public void createTable() throws SQLException, InterruptedException {
        jdbcMessageRepository.createTable();
    }

    /**
     * Метод удаляет таблицу message.
     *
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public void dropTable() throws SQLException, InterruptedException {
        jdbcMessageRepository.dropTable();
    }
}
