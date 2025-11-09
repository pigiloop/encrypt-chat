package ru.vinhome.util;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;

@UtilityClass
public class ConnectionUtil {

    private static final ArrayDeque<Connection> CONNECTION_POOL = new ArrayDeque<>();

    private static final String URL = PropertiesUtil.get("db.url.ip");

    private static final String USERNAME = PropertiesUtil.get("db.username");

    private static final String PASSWORD = PropertiesUtil.get("db.password");

    private static final Integer POOL_SIZE = PropertiesUtil.
            getPropertyToIntOrDefault("db.connection.pool.size", "5");

    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                CONNECTION_POOL.addLast(connection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Connection connection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static Connection getConnection() throws InterruptedException {
        while (CONNECTION_POOL.isEmpty()) {
            Thread.sleep(5_000);
        }

        return CONNECTION_POOL.pollFirst();
    }

    public static void returnConnection(final Connection connection) {
        CONNECTION_POOL.addLast(connection);
    }
}
