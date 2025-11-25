package ru.vinhome.controller.integration;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.vinhome.util.PostgresTestContainer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContainerTest {

    @Test
    void test() throws URISyntaxException {
        PostgresTestContainer.start();
        PostgresTestContainer.initSQL(Paths.get(ContainerTest.class.getClassLoader().getResource("init_db.sql").toURI()));
    }

}
