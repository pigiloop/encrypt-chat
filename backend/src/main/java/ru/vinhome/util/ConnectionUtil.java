package ru.vinhome.util;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

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
    public static HikariDataSource hikariDataSource = null;


    /**
     * Задаёт размер пула
     *
     */
    private static final Integer POOL_SIZE = PropertiesUtil.
            getPropertiesFromSystemEnvOrPropertiesToInt("db.connection.pool.size", "5");

    static {
        initPool();
    }

    /**
     * Метод инициализации пула
     * Размер пула берётся из параметра db.connection.pool.size, если данный параметр не указан размер пула равен 5.
     *
     */
    private static void initPool() {
        /**
         * jdbcUrl=jdbc:postgresql://localhost:5432/postgres
         * username=postgres
         * password=admin
         * driverClassName=org.postgresql.Driver
         *
         * poolName=app-pg-pool
         * maximumPoolSize=20
         * minimumIdle=5
         * schema=public
         */
        final var config = new HikariConfig(PropertiesUtil.getHikariProperties("dataSource."));
        hikariDataSource = new HikariDataSource(config);

    }

    /**
     * Метод перезагрузки пула.
     * Используется при тестировании системы.
     *
     */
    public static void reloadPool() {
        hikariDataSource.close();
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
     * Статичный метод, получаем экземпляр подключения к базе данных.
     *
     * @return Возвращает подключение типа Connection из библиотеки java.sql
     * @throws SQLException выкидывает исключение если имеются проблемы с подключением
     * @see Connection
     */
    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }
}
