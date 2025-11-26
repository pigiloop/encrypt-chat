package ru.vinhome.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    public static final String DB_NAME_KEY = "db.name";

    public static final String DB_PORT_KEY = "db.port";

    public static final String IMAGE_VERSION_KEY_POSTGRESQL = "18.1-alpine3.22";
    public static final String SERVER_PORT_KEY = "server.port";
    public static final String SERVER_PORT_DEFAULT_VALUE = "8080";
    public static final String SERVER_HOST_KEY = "server.host";
    public static final String SERVER_HOST_DEFAULT_VALUE = "127.0.0.1";
    public static final String DB_USERNAME_KEY = "db.username";
    public static final String DB_PASSWORD_KEY = "db.password";
    public static final String DB_URL_KEY = "db.url";
    public static final String DB_CONNECTION_POOL_SIZE_KEY = "db.connection.pool.size";
    public static final String DB_CONNECTION_POOL_SIZE_DEFAULT_VALUE = "5";
    public static final String LOG_PATH_KEY = "log.path";
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

    private PropertiesUtil() {
        // NOOP
    }

    public static String get(final String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getOrElse(final String key, final String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static int getPropertyToInt(final String key) {
        return Integer.parseInt(get(key));
    }

    public static int getPropertyToIntOrDefault(String key, String defaultValue) {
        return Integer.parseInt(get(key) == null ? defaultValue : get(key));
    }
}
