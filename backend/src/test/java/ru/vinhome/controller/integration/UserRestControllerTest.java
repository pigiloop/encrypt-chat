package ru.vinhome.controller.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vinhome.WrapperJettyServer;
import ru.vinhome.repository.JdbcMessageRepositoryImpl;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.util.ConnectionUtil;
import ru.vinhome.util.PostgresTestContainer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.sql.SQLException;

public class UserRestControllerTest {

    private static final int SERVER_PORT = 8888;

    private static final String BASE_URL = "http://localhost:" + SERVER_PORT;

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    private static final WrapperJettyServer WRAPPER_JETTY_SERVER = new WrapperJettyServer();

    @BeforeAll
    static void startJettyServer() throws Exception {
        WRAPPER_JETTY_SERVER.start();
        PostgresTestContainer.start();
        ConnectionUtil.reloadPool();
    }

    @AfterAll
    static void stopJettyServer() throws Exception {
        PostgresTestContainer.stop();
        WRAPPER_JETTY_SERVER.close();
    }

    @BeforeEach
    void initSQL() throws URISyntaxException, InterruptedException {
        PostgresTestContainer.initSQL(Paths.get(ContainerTest.class.getClassLoader().getResource("init_db.sql").toURI()));
    }

    @AfterEach
    void dropTables() {
        JdbcMessageRepositoryImpl jdbcMessageRepository = new JdbcMessageRepositoryImpl();
        try {
            jdbcMessageRepository.dropTable();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        JdbcUserRepositoryImpl jdbcUserRepository = new JdbcUserRepositoryImpl();
        try {
            jdbcUserRepository.dropTable();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void findAll() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/api/v1/users"))
                .GET()
                .header("Accept", "application/json")
                .build();


        System.out.println(HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString()));
    }

    @Test
    void findById() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/api/v1/users/1"))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> httpResponse = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());


        System.out.println(httpResponse);
    }

}
