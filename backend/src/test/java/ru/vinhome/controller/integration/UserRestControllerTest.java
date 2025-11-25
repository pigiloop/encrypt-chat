package ru.vinhome.controller.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vinhome.WrapperJettyServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserRestControllerTest {

    private static final int SERVER_PORT = 8888;

    private static final String BASE_URL = "http://localhost:" + SERVER_PORT;

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    private static final WrapperJettyServer WRAPPER_JETTY_SERVER = new WrapperJettyServer();

    @BeforeAll
    static void startJettyServer() throws Exception {
        WRAPPER_JETTY_SERVER.start();
    }

    @AfterAll
    static void stopJettyServer() throws Exception {
        WRAPPER_JETTY_SERVER.close();
    }

    @Test
    void test() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/api/v1/users"))
                .GET()
                .header("Accept", "application/json")
                .build();



        System.out.println(HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString()));
    }
}
