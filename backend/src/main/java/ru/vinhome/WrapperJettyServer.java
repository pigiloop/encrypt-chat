package ru.vinhome;

import lombok.Getter;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.servlet.ServletContainer;
import ru.vinhome.config.JerseyConfig;
import ru.vinhome.util.PropertiesUtil;

/**
 * Класс обёртка WrapperJettyServer реализующий jetty сервер.
 *
 */
@Getter
public class WrapperJettyServer implements AutoCloseable {

    /**
     * Приватное поле PORT задающий номер порта сервера
     *
     */
    private static final int PORT = PropertiesUtil.getPropertyToIntOrDefault(
            PropertiesUtil.SERVER_PORT_KEY,
            PropertiesUtil.SERVER_PORT_DEFAULT_VALUE
    );

    /**
     * Приватное поле server создаёт новый экземпляр класса Server по указанному номеру порта PORT
     *
     */
    private final Server server = new Server(PORT);

    /**
     * Конструктор класса WrapperJettyServer. Устанавливает Handler для сервера указывая в контексте путь к серверу
     * в адресной строке. Создаёт сервлет с указанием класса обработчика сервера.
     */
    public WrapperJettyServer() {

        final var contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        server.setHandler(contextHandler);

        final var jerseyServlet = new ServletHolder(new ServletContainer());
        jerseyServlet.setInitParameter(
                "jakarta.ws.rs.Application",
                JerseyConfig.class.getCanonicalName()
        );

        contextHandler.addServlet(jerseyServlet, "/api/*");

    }

    /**
     * Запуск сервера
     *
     * @throws Exception в случае ошибки выкидывает исключение
     */
    public void start() throws Exception {
        server.start();
    }

    /**
     * Метод close. Проверяет запущен ли сервер и если он запущен то его останавливает и уничтожает. Тем самым
     * реализуется интерфейс autocloseable
     *
     * @throws Exception в случае ошибки выкидывает исключение
     */
    @Override
    public void close() throws Exception {
        if (server.isRunning()) {
            server.stop();
            server.destroy();
        }
    }

}
