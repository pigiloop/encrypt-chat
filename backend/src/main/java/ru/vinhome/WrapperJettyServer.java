package ru.vinhome;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.servlet.ServletContainer;
import ru.vinhome.config.JerseyConfig;
import ru.vinhome.util.PropertiesUtil;

public class WrapperJettyServer implements AutoCloseable {

    private static final int PORT = PropertiesUtil.getPropertyToIntOrDefault(
            PropertiesUtil.SERVER_PORT_KEY,
            PropertiesUtil.SERVER_PORT_DEFAULT_VALUE
    );

    private final Server server = new Server(PORT);

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

    public void start() throws Exception {
        server.start();
    }

    public Server getServer() {
        return server;
    }

    @Override
    public void close() throws Exception {
        if (server.isRunning()) {
            server.stop();
            server.destroy();
        }
    }

}
