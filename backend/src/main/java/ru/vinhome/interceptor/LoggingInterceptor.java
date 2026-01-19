package ru.vinhome.interceptor;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Provider
public class LoggingInterceptor implements ContainerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String target = requestContext.getUriInfo().getPath();
        String methodName = requestContext.getMethod();

        byte[] allBytes = requestContext.getEntityStream().readAllBytes();

        requestContext.setEntityStream(new ByteArrayInputStream(allBytes));

        String body = new String(allBytes, StandardCharsets.UTF_8);

        logger.info("Вызван метод {}.{}() содержащий {}", target, methodName, body);
    }
}
