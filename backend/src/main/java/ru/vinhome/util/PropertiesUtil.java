package ru.vinhome.util;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Утилитный класс PropertiesUtil реализующий подключение к базе данных.
 * В данном классе реализованы статичные методы для взаимодействия с конфигурационным файлом
 * "application.properties"
 *
 */
public class PropertiesUtil {

    private static final Map<String, String> SYSTEM_ENVIRONMENT = System.getenv();


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
    public static final String SERVER_PORT_KEY = "jetty.server.port";

    /**
     * Ключ к параметру server.host, адрес сервера
     */
    public static final String SERVER_HOST_KEY = "jetty.server.host";

    /**
     * Номер порта сервера по умолчанию
     */
    public static final String SERVER_PORT_DEFAULT_VALUE = "8080";

    /**
     * Ключ к параметру db.username, имя пользователя в базе данных
     */
    public static final String DB_USERNAME_KEY = "dataSource.username";

    /**
     * Ключ к параметру db.password, пароль пользователя в базе данных
     */
    public static final String DB_PASSWORD_KEY = "dataSource.password";

    public static final String DB_DRIVER_CLASS_NAME_KEY = "dataSource.driverClassName";

    public static final String DB_POOL_NAME_KEY = "dataSource.poolName";

    public static final String DB_MAXIMUM_POOL_SIZE_KEY = "dataSource.maximumPoolSize";

    /**
     * Ключ к параметру db.connection.pool.size, размер пула соединений
     */
    public static final String DB_MINIMUM_IDLE_KEY = "dataSource.minimumIdle";

    public static final String DB_SCHEMA = "dataSource.schema";


    /**
     * Ключ к параметру db.url, адресная строка подключения к базе данных
     */
    public static final String DB_URL_KEY = "dataSource.jdbcUrl";

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

    public static Properties getHikariProperties(final String prefix) {
        final var res = new Properties();

        for (String key : PROPERTIES.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                String targetKey = key.substring(prefix.length());

                if (SYSTEM_ENVIRONMENT.containsKey(targetKey)) {
                    res.setProperty(targetKey, SYSTEM_ENVIRONMENT.get(targetKey));
                } else {
                    res.setProperty(targetKey, PROPERTIES.getProperty(key));
                }
            }
        }

        return res;
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
     *
     * @param key ключ строкового типа по которому необходимо получить данные
     * @return возвращаемая строка из файла настроек
     */
    private static String get(final String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Статический метод get считывает параметр по его ключу, если ключ недоступен то берётся значение по умолчанию
     *
     * @param key          ключ строкового типа по которому необходимо получить данные.
     * @param defaultValue значение по умолчанию
     * @return возвращаемая строка из файла настроек
     */
    private static String getOrElse(final String key, final String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    /**
     * Статический метод getPropertiesFromSystemEnvOrPropertiesToInt считывает параметр по его ключу и возвращает
     * его.
     * В начале по ключу ищется значение в системных переменных, если ключ недоступен, то берётся значение
     * из файла конфигурации "application.properties", то возвращает null.
     *
     * @param key          ключ строкового типа по которому необходимо получить данные.
     * @return возвращаемое значение
     */
    public static String getPropertiesFromSystemEnvOrProperties(String key) {

        if (SYSTEM_ENVIRONMENT.containsKey(key)) {
            return SYSTEM_ENVIRONMENT.get(key);
        }

        return PropertiesUtil.get(key);
    }

    /**
     * Статический метод getPropertiesFromSystemEnvOrPropertiesToInt считывает параметр по его ключу и возвращает
     * его.
     * В начале по ключу ищется значение в системных переменных, если ключ недоступен, то берётся значение
     * из файла конфигурации "application.properties", если и там нет то берётся значение из аргумента defaultValue
     *
     * @param key          ключ строкового типа по которому необходимо получить данные.
     * @return возвращаемое значение
     */
    public static int getPropertiesFromSystemEnvOrPropertiesToInt(String key) {
        return Integer.parseInt(getPropertiesFromSystemEnvOrProperties(key));
    }

    /**
     * Статический метод getPropertiesFromSystemEnvOrPropertiesToInt считывает параметр по его ключу и возвращает
     * его.
     * В начале по ключу ищется значение в системных переменных, если ключ недоступен, то берётся значение
     * из файла конфигурации "application.properties", если и там нет то берётся значение из аргумента defaultValue
     *
     * @param key          ключ строкового типа по которому необходимо получить данные.
     * @param defaultValue значение по умолчанию
     * @return возвращаемое значение
     */
    public static String getPropertiesFromSystemEnvOrProperties(String key, String defaultValue) {

        if (SYSTEM_ENVIRONMENT.containsKey(key)) {
            return SYSTEM_ENVIRONMENT.get(key);
        } else if (get(key) == null) {
            return defaultValue;
        }
        return get(key);
    }

    /**
     * Статический метод getPropertiesFromSystemEnvOrPropertiesToInt считывает параметр по его ключу и преобразовывает
     * его в целое число.
     * В начале по ключу ищется значение в системных переменных, если ключ недоступен, то берётся значение
     * из файла конфигурации "application.properties", если и там нет то берётся значение из аргумента defaultValue
     *
     * @param key          ключ строкового типа по которому необходимо получить данные.
     * @param defaultValue значение по умолчанию
     * @return возвращаемое значение
     */
    public static int getPropertiesFromSystemEnvOrPropertiesToInt(String key, String defaultValue) {

        return Integer.parseInt(getPropertiesFromSystemEnvOrProperties(key, defaultValue));
    }

}
