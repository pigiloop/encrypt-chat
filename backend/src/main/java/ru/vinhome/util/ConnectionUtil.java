package ru.vinhome.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;

/**
 * Утилитный класс ConnectionUtil реализующий подключение к базе данных.
 * В данном классе реализованы статичные методы получения и возвращения подключений к базе данных.
 *
 */
public final class ConnectionUtil {

    /**
     * Коллекция соединений типа ArrayDeque. Содержит коллекцию соединений.
     *
     */
    public static final ArrayDeque<Connection> CONNECTION_POOL = new ArrayDeque<>();

    /**
     * URL подключения к базе данных.
     *
     */
    private static final String URL = PropertiesUtil.get("db.url.ip");

    /**
     * Имя пользователя в базе данных.
     *
     */
    private static final String USERNAME = PropertiesUtil.get("db.username");

    /**
     * Пароль к базе данных.
     *
     */
    private static final String PASSWORD = PropertiesUtil.get("db.password");

    /**
     * Задаёт размер пула
     *
     */
    private static final Integer POOL_SIZE = PropertiesUtil.
            getPropertyToIntOrDefault("db.connection.pool.size", "5");

    static {
        initPool();
    }

    /**
     * Метод инициализации пула
     * Размер пула берётся из параметра db.connection.pool.size, если данный параметр не указан размер пула равен 5.
     *
     */
    private static void initPool() {

        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                CONNECTION_POOL.addLast(connection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Метод перезагрузки пула.
     * Используется при тестировании системы.
     *
     */
    public static void reloadPool() {
        CONNECTION_POOL.clear();
        initPool();
    }

    /**
     * Приватный конструктор.
     * Создан для того чтобы не создавался конструктор по умолчанию.
     *
     */
    private ConnectionUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Метод перезагрузки пула.
     * Используется при тестировании системы.
     *
     */
    private static Connection connection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Статичный метод, получаем экземпляр подключения к базе данных.
     *
     * @return Возвращает подключение типа Connection из библиотеки java.sql
     * @exception InterruptedException выкидывает исключение если все ресурсы заняты
     * @see Connection
     */
    public static Connection getConnection() throws InterruptedException {
        while (CONNECTION_POOL.isEmpty()) {
            Thread.sleep(5_000);
        }
        return CONNECTION_POOL.pollFirst();
    }

    /**
     * Статичный метод, возвращает подключение к базе данных.
     *
     * @param connection соединение, которое возвращаем данным методом.
     *
     */
    public static void returnConnection(final Connection connection) {
        if (CONNECTION_POOL.contains(connection)) {
            return;
        }
        CONNECTION_POOL.addLast(connection);
    }
}
