package ru.vinhome.service;

import ru.vinhome.controller.dto.UserCreateRequest;
import ru.vinhome.controller.dto.UserUpdateRequest;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final JdbcUserRepositoryImpl jdbcUserRepository;

    /**
     * Конструктор класса JdbcMessageServiceImpl принимающий в качестве параметра экземпляр класса
     * JdbcMessageRepository
     *
     * @param userRepository параметр типа JdbcUserRepositoryImpl
     * @see JdbcUserRepositoryImpl
     */
    public UserServiceImpl(JdbcUserRepositoryImpl userRepository) {
        this.jdbcUserRepository = userRepository;
    }

    /**
     * Метод выводит все записи таблицы users.
     *
     * @return возвращает список объектов типа User
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *
     */
    @Override
    public ArrayList<User> findAll() throws SQLException {
        return jdbcUserRepository.findAll();
    }

    /**
     * Метод выводит запись таблицы users по её уникальному идентификатору.
     *
     * @param id идентификатор типа Long
     * @return возвращает объект типа User
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public Optional<User> findById(Integer id) throws SQLException {
        return Optional.ofNullable(jdbcUserRepository.findById(id));
    }

    /**
     * Метод выводит запись таблицы по имени пользователя.
     *
     * @param userName имя пользователя типа String
     * @return возвращает объект типа User
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public User findByUsername(String userName) throws SQLException {
        return jdbcUserRepository.findByUsername(userName);
    }

    /**
     * Метод сохраняет запись таблицы user.
     *
     * @param obj экземпляр класса User
     * @return возвращает количество изменённых записей
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public int save(UserCreateRequest obj) throws SQLException {
        User user = User.builder()
                .userName(obj.userName())
                .email(obj.email())
                .firstName(obj.firstName())
                .lastName(obj.lastName())
                .password(obj.password())
                .age(obj.age())
                .build();
        return jdbcUserRepository.save(user);
    }

    /**
     * Метод изменяет запись таблицы по её уникальному идентификатору.
     *
     * @param id  идентификатор типа Long
     * @param obj экземпляр класса User
     * @return возвращает количество изменённых записей
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public int update(Integer id, UserUpdateRequest obj) throws SQLException {

        User user = jdbcUserRepository.findById(id);

        user.setFirstName(obj.firstName());
        user.setLastName(obj.lastName());
        user.setAge(obj.age());

        return jdbcUserRepository.update(id, user);
    }

    /**
     * Метод удаляет запись из таблицы по её уникальному идентификатору.
     *
     * @param id идентификатор типа Long
     * @return возвращает количество удалённых записей
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public int delete(Integer id) throws SQLException {
        return jdbcUserRepository.delete(id);
    }

    /**
     * Метод создаёт таблицу users
     *
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public void createTable() throws SQLException {
        jdbcUserRepository.createTable();
    }

    /**
     * Метод удаляет таблицу users
     *
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public void dropTable() throws SQLException {
        jdbcUserRepository.dropTable();
    }

    /**
     * Метод проверяет наличие записи электронной почты в таблице users.
     *
     * @param email электронная почта пользователя типа String
     * @return возвращает true или false в зависимости есть ли указанная электронная почта в таблице или нет.
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public boolean emailExist(String email) throws SQLException {
        return jdbcUserRepository.emailExist(email);
    }
}
