package ru.vinhome.repository;

import org.springframework.stereotype.Component;
import ru.vinhome.model.User;
import ru.vinhome.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс JdbcUserRepositoryImpl реализующий интерфейсы BaseRepository и UserRepository.
 * В данном интерфейсе описаны методы для реализации CRUD в базе данных с таблицей users.
 *
 * @see BaseRepository
 * @see UserRepository
 * @see User
 */
@Component
public class JdbcUserRepositoryImpl implements BaseRepository<User, Integer>, UserRepository {

    private static final String SELECT_ALL_SQL = """
            SELECT id, username, email, first_name, last_name, password, age
            FROM public.users;
            """;

    private static final String SELECT_BY_ID_SQL = """
            SELECT id, username, email, first_name, last_name, password, age
            FROM public.users
            WHERE id = ?;
            """;

    private static final String SELECT_BY_EMAIL_SQL = """
            SELECT id, username, email, first_name, last_name, password, age
            FROM public.users
            WHERE email = ?;
            """;

    private static final String SELECT_BY_USERNAME_SQL = """
            SELECT id, username, email, first_name, last_name, password, age
            FROM public.users
            WHERE username = ?;
            """;

    public static final String EXISTS_USER_BY_ID = """
            SELECT EXISTS (select * from public.users where id=?);
            """;

    private static final String INSERT_SQL = """
            INSERT INTO public.users(username, email, first_name, last_name, password, age)
            VALUES(?, ?, ?, ?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE public.users
            SET first_name = ?, last_name = ?, age = ?
            WHERE id = ?;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM public.users
            WHERE id = ?;
            """;

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS public.users (
                                   id SERIAL PRIMARY KEY,
                                   username VARCHAR(64) NOT NULL UNIQUE,
                                   email varchar(255) NOT NULL UNIQUE,
                                   first_name varchar(64),
                                   last_name varchar(64),
                                   password varchar(255),
                                   age integer NOT NULL
            );
            """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS users;
            """;

    /**
     * Метод выводит все записи таблицы users.
     *
     * @return возвращает список объектов типа User
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public ArrayList<User> findAll() throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = preparedStatement.executeQuery()
        ) {
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getInt(1))
                        .userName(resultSet.getString(2))
                        .email(resultSet.getString(3))
                        .firstName(resultSet.getString(4))
                        .lastName(resultSet.getString(5))
                        .password(resultSet.getString(6))
                        .age(resultSet.getInt(7))
                        .build());
            }
            return users;
        }
    }

    /**
     * Метод выводит запись таблицы users по её уникальному идентификатору используя существующее соединение connection типа Connection.
     *
     * @param id идентификатор типа Long
     * @return возвращает объект типа User
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see User
     * @see Connection
     * *
     */
    @Override
    public User findById(Integer id) throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL)
        ) {
// set
            preparedStatement.setObject(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return User.builder()
                            .id(resultSet.getInt(1))
                            .userName(resultSet.getString(2))
                            .email(resultSet.getString(3))
                            .firstName(resultSet.getString(4))
                            .lastName(resultSet.getString(5))
                            .password(resultSet.getString(6))
                            .age(resultSet.getInt(7))
                            .build();
                } else {
                    return null;
                }
            }
        }
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

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(SELECT_BY_USERNAME_SQL);
        ) {
            preparedStatement.setString(1, userName);
            try (
                    var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return User.builder()
                            .id(resultSet.getInt(1))
                            .userName(resultSet.getString(2))
                            .email(resultSet.getString(3))
                            .firstName(resultSet.getString(4))
                            .lastName(resultSet.getString(5))
                            .password(resultSet.getString(6))
                            .age(resultSet.getInt(7))
                            .build();
                } else {
                    return null;
                }
            }
        }
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

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL_SQL);
        ) {
            preparedStatement.setString(1, email);
            try (
                    var resultSet = preparedStatement.executeQuery();
            ) {
                return resultSet.next();
            }
        }
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
    public int save(final User obj) throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(INSERT_SQL);
        ) {
            preparedStatement.setString(1, obj.getUserName());
            preparedStatement.setString(2, obj.getEmail());
            preparedStatement.setString(3, obj.getFirstName());
            preparedStatement.setString(4, obj.getLastName());
            preparedStatement.setString(5, obj.getPassword());
            preparedStatement.setInt(6, obj.getAge());
            preparedStatement.execute();
            return preparedStatement.getUpdateCount();
        }


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
    public int update(Integer id, User obj) throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(UPDATE_SQL);
        ) {
            preparedStatement.setString(1, obj.getFirstName());
            preparedStatement.setString(2, obj.getLastName());
            preparedStatement.setInt(3, obj.getAge());
            preparedStatement.setLong(4, id);
            preparedStatement.execute();

            return preparedStatement.getUpdateCount();
        }
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

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(DELETE_SQL);
        ) {
            int updateCount = 0;

            preparedStatement.setObject(1, id);
            preparedStatement.execute();
            updateCount = preparedStatement.getUpdateCount();
            return updateCount;
        }
    }

    /**
     * Метод создаёт таблицу users
     *
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    public void createTable() throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL);
        ) {
            preparedStatement.execute();
        }
    }

    /**
     * Метод удаляет таблицу users
     *
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    public void dropTable() throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(DROP_TABLE_SQL);
        ) {
            preparedStatement.execute();
        }
    }

}
