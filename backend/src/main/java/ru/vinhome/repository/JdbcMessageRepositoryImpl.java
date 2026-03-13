package ru.vinhome.repository;

import ru.vinhome.model.Message;
import ru.vinhome.util.ConnectionUtil;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Класс JdbcMessageRepositoryImpl реализующий интерфейс BaseRepository.
 * В данном классе реализованы методы CRUD, для манипуляций в базе данных с таблицей message
 *
 * @see BaseRepository
 * @see Message
 */
public class JdbcMessageRepositoryImpl implements BaseRepository<Message, Integer> {

    private static final String SELECT_ALL_SQL = """
            SELECT id, id_sender, id_recipient, message, created_at
            FROM message f;
            """;

    private static final String SELECT_BY_ID_SQL = """
            SELECT id, id_sender, id_recipient, message, created_at
            FROM message
            WHERE id = ?;
            """;

    private static final String INSERT_SQL = """
            INSERT INTO message(id_sender, id_recipient, message)
            VALUES(?, ?, ?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE message
            SET id_sender = ?, id_recipient = ?, message = ?, created_at = ?
            WHERE id = ?;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM message
            WHERE id = ?;
            """;

    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS message (
                                   id SERIAL PRIMARY KEY,
                                   id_sender INTEGER REFERENCES users(id),
                                   id_recipient INTEGER REFERENCES users(id),
                                   message text NOT NULL,
                                   created_at TIMESTAMP DEFAULT NOW()
            );
            """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS message;
            """;

    /**
     * Метод выводит все записи таблицы message.
     *
     * @return возвращает список объектов типа Message
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public ArrayList<Message> findAll() throws SQLException {
        final var messages = new ArrayList<Message>();

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                messages.add(Message.createMessage(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5).toLocalDateTime()
                ));
            }
        }

        return messages;
    }

    /**
     * Метод выводит запись таблицы message по её уникальному идентификатору используя существующее соединение connection типа Connection.
     *
     * @param id идентификатор типа Long
     * @return возвращает объект типа Message
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see Message
     * *
     */
    @Override
    public Message findById(Integer id) throws SQLException {


        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL);
        ) {
            preparedStatement.setObject(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Message.createMessage(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getString(4),
                            resultSet.getTimestamp(5).toLocalDateTime()
                    );
                } else {
                    return null;
                }

            }

        }

    }

    /**
     * Метод сохраняет запись в таблице message.
     *
     * @param obj объект типа Message
     * @return возвращает количество изменённых записей в таблице message
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see Message
     *
     */
    @Override
    public int save(Message obj) throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(INSERT_SQL);
        ) {
            preparedStatement.setInt(1, obj.getSenderId());
            preparedStatement.setInt(2, obj.getRecipientId());
            preparedStatement.setString(3, obj.getMessage());
            preparedStatement.execute();

            return preparedStatement.getUpdateCount();
        }

    }

    /**
     * Метод изменяет запись в таблице message.
     *
     * @param id  идентификатор типа Long
     * @param obj объект типа Message
     * @return возвращает количество изменённых записей
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     * @see Message
     * *
     */
    @Override
    public int update(Integer id, Message obj) throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(UPDATE_SQL);
        ) {
            preparedStatement.setInt(1, obj.getSenderId());
            preparedStatement.setInt(2, obj.getRecipientId());
            preparedStatement.setString(3, obj.getMessage());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(obj.getCreatedAt()));
            preparedStatement.setInt(5, id);
            preparedStatement.execute();

            return preparedStatement.getUpdateCount();
        }
    }

    /**
     * Метод удаляет запись из таблицы message.
     *
     * @param id идентификатор типа Long
     * @return возвращает количество изменённых записей
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public int delete(Integer id) throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(DELETE_SQL);
        ) {
            preparedStatement.setObject(1, id);
            preparedStatement.execute();

            return preparedStatement.getUpdateCount();
        }
    }

    /**
     * Метод создаёт таблицу message.
     *
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public void createTable() throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL);
        ) {
            preparedStatement.execute();
        }
    }

    /**
     * Метод удаляет таблицу message.
     *
     * @throws SQLException         возникает в случае ошибки запроса к базе данных
     *                              *
     */
    @Override
    public void dropTable() throws SQLException {

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(DROP_TABLE_SQL);
        ) {
            preparedStatement.execute();
        }

    }
}
