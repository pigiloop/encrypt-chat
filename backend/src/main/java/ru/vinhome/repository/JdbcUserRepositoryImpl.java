package ru.vinhome.repository;

import ru.vinhome.model.User;
import ru.vinhome.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс JdbcUserRepositoryImpl реализующий интерфейсы BaseRepository и UserRepository.
 * В данном интерфейсе описаны методы для реализации CRUD в базе данных с таблицей users.
 * @see BaseRepository
 * @see UserRepository
 * @see User
 */
public class JdbcUserRepositoryImpl implements BaseRepository<User, Long>, UserRepository {

    private static final String SELECT_ALL_SQL = """
            SELECT id, username, email, first_name, last_name, password, age
            FROM users;
            """;

    private static final String SELECT_BY_ID_SQL = """
            SELECT id, username, email, first_name, last_name, password, age
            FROM users
            WHERE id = ?;
            """;

    private static final String SELECT_BY_EMAIL_SQL = """
            SELECT id, username, email, first_name, last_name, password, age
            FROM users
            WHERE email = ?;
            """;

    private static final String SELECT_BY_USERNAME_SQL = """
            SELECT id, username, email, first_name, last_name, password, age
            FROM users
            WHERE username = ?;
            """;

    private static final String INSERT_SQL = """
            INSERT INTO users(username, email, first_name, last_name, password, age)
            VALUES(?, ?, ?, ?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE users
            SET first_name = ?, last_name = ?, age = ?
            WHERE id = ?;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?;
            """;

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS users (
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
     * @return возвращает список объектов типа User
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public ArrayList<User> findAll() throws InterruptedException, SQLException {
        final var connection = ConnectionUtil.getConnection();

        final var preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        ConnectionUtil.returnConnection(connection);

        ArrayList<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(User.builder()
                    .id(resultSet.getLong(1))
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

    /**
     * Метод выводит запись таблицы users по её уникальному идентификатору.
     * @return возвращает объект типа User
     * @param id идентификатор типа Long
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * @see User
     * *
     */
    @Override
    public User findById(Long id) throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();

        return findById(id, connection);


    }

    /**
     * Метод выводит запись таблицы users по её уникальному идентификатору используя существующее соединение connection типа Connection.
     * @param id идентификатор типа Long
     * @param connection параметр типа Connection, нужен для использования существующего соединения
     * @return возвращает объект типа User
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * @see User
     * @see Connection
     * *
     */
    @Override
    public User findById(Long id, Connection connection) throws SQLException, InterruptedException {
        final var preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL);
        preparedStatement.setObject(1, id);

        final var resultSet = preparedStatement.executeQuery();
        ConnectionUtil.returnConnection(connection);
        if (resultSet.next()) {
            return User.builder()
                    .id(resultSet.getLong(1))
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
        final var connection = ConnectionUtil.getConnection();

        final var preparedStatement = connection.prepareStatement(SELECT_BY_USERNAME_SQL);
        preparedStatement.setString(1, userName);

        final var resultSet = preparedStatement.executeQuery();
        ConnectionUtil.returnConnection(connection);

        if (resultSet.next()) {
            return User.builder()
                    .id(resultSet.getLong(1))
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
        final var connection = ConnectionUtil.getConnection();

        final var preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL_SQL);
        preparedStatement.setString(1, email);

        final var resultSet = preparedStatement.executeQuery();
        ConnectionUtil.returnConnection(connection);

        return resultSet.next();

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
    public int save(final User obj) throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();

        final var preparedStatement = connection.prepareStatement(INSERT_SQL);
        preparedStatement.setString(1, obj.getUserName());
        preparedStatement.setString(2, obj.getEmail());
        preparedStatement.setString(3, obj.getFirstName());
        preparedStatement.setString(4, obj.getLastName());
        preparedStatement.setString(5, obj.getPassword());
        preparedStatement.setInt(6, obj.getAge());
        preparedStatement.execute();
        ConnectionUtil.returnConnection(connection);
        return preparedStatement.getUpdateCount();
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
        final var connection = ConnectionUtil.getConnection();

        final var preparedStatement = connection.prepareStatement(UPDATE_SQL);
        preparedStatement.setString(1, obj.getFirstName());
        preparedStatement.setString(2, obj.getLastName());
        preparedStatement.setInt(3, obj.getAge());
        preparedStatement.setLong(4, id);
        preparedStatement.execute();
        ConnectionUtil.returnConnection(connection);

        return preparedStatement.getUpdateCount();
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
        final var connection = ConnectionUtil.getConnection();
        int updateCount = 0;

        try (var preparedStatement = connection.prepareStatement(DELETE_SQL);) {
            preparedStatement.setObject(1, id);
            preparedStatement.execute();
            updateCount = preparedStatement.getUpdateCount();
        } finally {
            ConnectionUtil.returnConnection(connection);
        }

            return updateCount;
    }

    /**
     * Метод создаёт таблицу users
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * *
     */
    public void createTable() throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();
        final var preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL);
        preparedStatement.execute();
        ConnectionUtil.returnConnection(connection);
    }

    /**
     * Метод удаляет таблицу users
     * @throws InterruptedException возникает в случае ошибки получения подключения
     * @throws SQLException возникает в случае ошибки запроса к базе данных
     * *
     */
    public void dropTable() throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();
        final var preparedStatement = connection.prepareStatement(DROP_TABLE_SQL);
        preparedStatement.execute();
        ConnectionUtil.returnConnection(connection);
    }

}
