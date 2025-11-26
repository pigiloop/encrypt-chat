package ru.vinhome.util;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresTestContainer {

    private static final String DB_USERNAME = PropertiesUtil.get(PropertiesUtil.DB_USERNAME_KEY);
    private static final String DB_PASSWORD = PropertiesUtil.get(PropertiesUtil.DB_PASSWORD_KEY);
    private static final int DB_PORT = PropertiesUtil.getPropertyToInt(PropertiesUtil.DB_PORT_KEY);
    private static final String DB_NAME = PropertiesUtil.get(PropertiesUtil.DB_NAME_KEY);
    private static final String IMAGE_VERSION = PropertiesUtil.getOrElse(
            PropertiesUtil.IMAGE_VERSION_KEY_POSTGRESQL,
            "18.1-alpine3.22");

    private static PostgreSQLContainer<?> postgreSQLContainer;

    private PostgresTestContainer() { }

    public static PostgreSQLContainer<?> getInstance() {
        if (postgreSQLContainer == null) {
            postgreSQLContainer = new PostgreSQLContainer<>("postgres:" + IMAGE_VERSION)
                    .withUsername(DB_USERNAME)
                    .withPassword(DB_PASSWORD)
                    .withExposedPorts(DB_PORT)
                    .withDatabaseName(DB_NAME)
                    .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                            new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(DB_PORT), new ExposedPort(DB_PORT)))));
        }
        return postgreSQLContainer;
    }

    public static void start() {
        getInstance().start();
    }

    public static void stop() {
        getInstance().stop();
    }

    public static String getJdbcUrl() {
        return getInstance().getJdbcUrl();
    }

    public static String getDbUsername() {
        return getInstance().getUsername();
    }

    public static String getDbPassword() {
        return getInstance().getPassword();
    }

    public static int getDbPort() {
        return getInstance().getMappedPort(DB_PORT);
    }

    public static void initSQL(Path pathToSqlInit) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(Files.readString(pathToSqlInit))) {
            preparedStatement.execute();

        } catch (InterruptedException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
