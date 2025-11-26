package ru.vinhome.repository;

import ru.vinhome.model.Message;
import ru.vinhome.util.ConnectionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class JdbcMessageRepositoryImpl implements BaseRepository<Message, Long> {

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
            INSERT INTO message(id, id_sender, id_recipient, message)
            VALUES(?, ?, ?, ?);
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
                                   id integer PRIMARY KEY,
                                   id_sender integer REFERENCES users(id),
                                   id_recipient integer REFERENCES users(id),
                                   message text NOT NULL,
                                   created_at timestamp DEFAULT NOW()
            );
            """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS message;
            """;


    @Override
    public ArrayList<Message> findAll() throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        final var preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Message> messages = new ArrayList<>();

        while (resultSet.next()) {
            messages.add(Message.createMessage(
                    resultSet.getLong(1),
                    jdbcUserRepository.findById(resultSet.getLong(2), connection),
                    jdbcUserRepository.findById(resultSet.getLong(3), connection),
                    resultSet.getString(4),
                    resultSet.getTimestamp(5).toLocalDateTime()
            ));
        }
        ConnectionUtil.returnConnection(connection);

        return messages;
    }

    @Override
    public Message findById(Long id) throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();
        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();

        final var preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL);
        preparedStatement.setObject(1, id);

        final var resultSet = preparedStatement.executeQuery();
        ConnectionUtil.returnConnection(connection);
        if (resultSet.next()) {
            return Message.createMessage(
                    resultSet.getLong(1),
                    jdbcUserRepository.findById(resultSet.getLong(2), connection),
                    jdbcUserRepository.findById(resultSet.getLong(3), connection),
                    resultSet.getString(4),
                    resultSet.getTimestamp(5).toLocalDateTime()
            );
        } else {
            return null;
        }

    }

    @Override
    public int save(Message obj) throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();

        final var preparedStatement = connection.prepareStatement(INSERT_SQL);
        preparedStatement.setLong(1, obj.getId());
        preparedStatement.setLong(2, obj.getSender().getId());
        preparedStatement.setLong(3, obj.getRecipient().getId());
        preparedStatement.setString(4, obj.getMessage());
        preparedStatement.execute();
        ConnectionUtil.returnConnection(connection);
        return preparedStatement.getUpdateCount();
    }

    @Override
    public int update(Long id, Message obj) throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();

        final var preparedStatement = connection.prepareStatement(UPDATE_SQL);
        preparedStatement.setLong(1, obj.getSender().getId());
        preparedStatement.setLong(2, obj.getRecipient().getId());
        preparedStatement.setString(3, obj.getMessage());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(obj.getCreatedAt()));
        preparedStatement.setLong(5, id);
        preparedStatement.execute();
        ConnectionUtil.returnConnection(connection);

        return preparedStatement.getUpdateCount();
    }

    @Override
    public int delete(Long id) throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();

        final var preparedStatement = connection.prepareStatement(DELETE_SQL);
        preparedStatement.setObject(1, id);
        preparedStatement.execute();
        ConnectionUtil.returnConnection(connection);

        return preparedStatement.getUpdateCount();
    }

    @Override
    public void createTable() throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();
        final var preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL);
        preparedStatement.execute();
        ConnectionUtil.returnConnection(connection);
    }

    @Override
    public void dropTable() throws SQLException, InterruptedException {
        final var connection = ConnectionUtil.getConnection();

        try (var preparedStatement = connection.prepareStatement(DROP_TABLE_SQL);) {
            preparedStatement.execute();
        } finally {
            ConnectionUtil.returnConnection(connection);
        }
    }
}
