package ru.vinhome.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Утилитный класс PropertiesUtil реализующий подключение к базе данных.
 * В данном классе реализованы статичные методы для взаимодействия с конфигурационным файлом
 * "application.properties"
 *
 */
public class PropertiesUtil {

    /**
     * Ключ к параметру db.name имя базы данных
     */
    public static final String DB_NAME_KEY = "db.name";

    /**
     * Ключ к параметру db.port номер порта у базы данных
     */
    public static final String DB_PORT_KEY = "db.port";

    /**
     * Ключ к параметру db.image.version версия образа базы данных
     */
    public static final String IMAGE_VERSION_KEY_POSTGRESQL = "db.image.version";

    /**
     * Ключ к параметру server.port, номер порта сервера
     */
    public static final String SERVER_PORT_KEY = "server.port";

    /**
     * Номер порта сервера по умолчанию
     */
    public static final String SERVER_PORT_DEFAULT_VALUE = "8080";

    /**
     * Ключ к параметру server.host, адрес хоста сервера
     */
    public static final String SERVER_HOST_KEY = "server.host";

    /**
     * Адрес хоста сервера по умолчанию
     */
    public static final String SERVER_HOST_DEFAULT_VALUE = "127.0.0.1";

    /**
     * Ключ к параметру db.username, имя пользователя в базе данных
     */
    public static final String DB_USERNAME_KEY = "db.username";

    /**
     * Ключ к параметру db.password, пароль пользователя в базе данных
     */
    public static final String DB_PASSWORD_KEY = "db.password";

    /**
     * Ключ к параметру db.url, адресная строка подключения к базе данных
     */
    public static final String DB_URL_KEY = "db.url";

    /**
     * Ключ к параметру db.connection.pool.size, размер пула соединений
     */
    public static final String DB_CONNECTION_POOL_SIZE_KEY = "db.connection.pool.size";

    /**
     * Размер пула по умолчанию
     */
    public static final String DB_CONNECTION_POOL_SIZE_DEFAULT_VALUE = "5";

    /**
     * Путь к файлу лога
     */
    public static final String LOG_PATH_KEY = "logger.file.path";

    /**
     * Приватное статическое поле типа Properties
     *
     */
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            final var resourceStream = PropertiesUtil.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");
            PROPERTIES.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Приватный конструктор.
     * Создан для того чтобы не создавался конструктор по умолчанию.
     *
     */
    private PropertiesUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Статический метод get считывает параметр по его ключу
     * @param  key ключ строкового типа по которому необходимо получить данные
     * @return возвращаемая строка из файла настроек
     */
    public static String get(final String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Статический метод get считывает параметр по его ключу, если ключ недоступен то берётся значение по умолчанию
     * @param  key ключ строкового типа по которому необходимо получить данные.
     * @param  defaultValue значение по умолчанию
     *
     * @return возвращаемая строка из файла настроек
     */
    public static String getOrElse(final String key, final String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    /**
     * Статический метод get считывает параметр по его ключу и преобразовывает его в целое число.
     * @param  key ключ строкового типа по которому необходимо получить данные.
     *
     * @return возвращаемая строка из файла настроек
     */
    public static int getPropertyToInt(final String key) {
        return Integer.parseInt(get(key));
    }

    /**
     * Статический метод get считывает параметр по его ключу и преобразовывает его в целое число.
     * Если ключ недоступен, то берётся значение по умолчанию
     * @param  key ключ строкового типа по которому необходимо получить данные.
     * @param  defaultValue значение по умолчанию
     *
     * @return возвращаемое целое число из файла настроек
     */
    public static int getPropertyToIntOrDefault(String key, String defaultValue) {
        return Integer.parseInt(get(key) == null ? defaultValue : get(key));
    }
}
