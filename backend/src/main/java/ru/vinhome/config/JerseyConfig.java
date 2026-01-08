package ru.vinhome.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Класс утилиты для конфигурации сервера на базе Jersey.
 *
 * <p>Предназначен для конфигурирования веб сервиса на базе фреймворка Jersey.
 *
 * <p>Пример использования:
 *
 * <pre>
 *    final var jerseyServlet = new ServletHolder(new ServletContainer());
 *         jerseyServlet.setInitParameter(
 *                 "jakarta.ws.rs.Application",
 *                 JerseyConfig.class.getCanonicalName()
 *         )
 * </pre>
 */
@ApplicationPath("/api/")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("ru.vinhome.controller");
        register(JacksonFeature.class);
    }

}
