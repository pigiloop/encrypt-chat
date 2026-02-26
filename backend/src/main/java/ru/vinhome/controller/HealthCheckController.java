package ru.vinhome.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


/**
 * Класс api сервиса для тестирования Rest контроллера.
 *
 */
@Path("/v1/test")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class HealthCheckController {
    /**
     * Метод заглушка выдающий положительный ответ
     *
     * @return возвращает положительный ответ с кодом 200
     */
    @GET
    public Response get() {
        return Response
                .status(Response.Status.OK)
                .entity("I'm healthy")
                .build();
    }
}