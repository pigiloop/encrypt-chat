package ru.vinhome.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    public static final String SERVER_PORT_KEY = "server.port";

    public static final String SERVER_PORT_DEFAULT_VALUE = "8080";

    public static final String SERVER_HOST_KEY = "server.host";

    public static final String SERVER_HOST_DEFAULT_VALUE = "127.0.0.1";

    public static final String BD_USERNAME_KEY = "bd.username";

    public static final String BD_PASSWORD_KEY = "bd.password";

    public static final String BD_URL_KEY = "bd.url";

    public static final String BD_CONNECTION_POOL_SIZE_KEY = "db.connection.pool.size";

    public static final String BD_CONNECTION_POOL_SIZE_DEFAULT_VALUE = "5";

    public static final String LOG_PATH_KEY = "log.path";

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
