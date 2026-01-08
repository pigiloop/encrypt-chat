package ru.vinhome.service;

import ru.vinhome.model.Message;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcMessageRepositoryImpl;
import ru.vinhome.repository.JdbcUserRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс UserServiceImpl реализующий интерфейсы CrudService и TableManagement.
 * В данном классе реализованы методы сервис слоя User, для манипуляций данными типа User
 *
 * @see CrudService
 * @see Message
 */
public class UserServiceImpl implements CrudService<User, Long>, TableManagement, IUserService {

    private JdbcUserRepositoryImpl jdbcUserRepository = null;

    /**
     * Конструктор класса JdbcMessageServiceImpl принимающий в качестве параметра экземпляр класса
     * JdbcMessageRepository
     *
     * @param userRepository параметр типа JdbcUserRepositoryImpl
     * @see JdbcMessageRepositoryImpl
     * @see Message
     */
    public UserServiceImpl(JdbcUserRepositoryImpl userRepository) {
        this.jdbcUserRepository = userRepository;
    }

    /**
     * Метод выводит все записи таблицы users.
     *
     * @return возвращает список объектов типа User
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *
     */
    @Override
    public ArrayList<User> findAll() throws SQLException, InterruptedException {
        return jdbcUserRepository.findAll();
    }

    /**
     * Метод выводит запись таблицы users по её уникальному идентификатору.
     * @param id идентификатор типа Long
     * @return возвращает объект типа User
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public User findById(Long id) throws SQLException, InterruptedException {
        return jdbcUserRepository.findById(id);
    }

    /**
     * Метод выводит запись таблицы по имени пользователя.
     * @param userName имя пользователя типа String
     * @return возвращает объект типа User
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public User findByUsername(String userName) throws SQLException, InterruptedException {
        return jdbcUserRepository.findByUsername(userName);
    }

    /**
     * Метод сохраняет запись таблицы user.
     * @param obj экземпляр класса User
     * @return возвращает количество изменённых записей
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public int save(User obj) throws SQLException, InterruptedException {
        return jdbcUserRepository.save(obj);
    }

    /**
     * Метод изменяет запись таблицы по её уникальному идентификатору.
     * @param id идентификатор типа Long
     * @param obj экземпляр класса User
     * @return возвращает количество изменённых записей
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public int update(Long id, User obj) throws SQLException, InterruptedException {
        return jdbcUserRepository.update(id, obj);
    }

    /**
     * Метод удаляет запись из таблицы по её уникальному идентификатору.
     * @param id идентификатор типа Long
     * @return возвращает количество удалённых записей
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public int delete(Long id) throws SQLException, InterruptedException {
        return jdbcUserRepository.delete(id);
    }

    /**
     * Метод создаёт таблицу users
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * *
     */
    @Override
    public void createTable() throws SQLException, InterruptedException {
        jdbcUserRepository.createTable();
    }

    /**
     * Метод удаляет таблицу users
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * *
     */
    @Override
    public void dropTable() throws SQLException, InterruptedException {
        jdbcUserRepository.dropTable();
    }

    /**
     * Метод проверяет наличие записи электронной почты в таблице users.
     * @param email электронная почта пользователя типа String
     * @return возвращает true или false в зависимости есть ли указанная электронная почта в таблице или нет.
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * *
     */
    @Override
    public boolean emailExist(String email) throws SQLException, InterruptedException {
        return jdbcUserRepository.emailExist(email);
    }
}
