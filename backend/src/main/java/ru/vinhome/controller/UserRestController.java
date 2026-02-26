package ru.vinhome.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.vinhome.controller.dto.UserCreateRequest;
import ru.vinhome.controller.dto.UserUpdateRequest;
import ru.vinhome.service.UserService;


import java.sql.SQLException;

/**
 * Класс контроллер пользователя
 */
@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRestController {

    private final UserService userService;

    @Inject
    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Выдаёт все записи пользователей
     *
     * @return возвращает http ответ с данными о пользователях в формате JSON
     */
    @GET
    public Response findAll() {
        try {
            return Response.status(Response.Status.OK)
                    .entity(userService.findAll())
                    .build();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Выдаёт запись пользователя по его идентификатору
     *
     * @param id идентификатор пользователя
     * @return возвращает объект класса User
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") final int id) {
        try {
            try {
                return Response.ok()
                        .entity(userService.findById(id))
                        .build();
            } catch (SQLException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (NullPointerException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("e.getMessage()")
                    .build();
        }
    }

    /**
     * Выдаёт запись пользователя по его имени пользователя
     *
     * @param userName имя пользователя
     * @return возвращает объект класса User
     */
    @GET
    @Path("/username={username}")
    public Response findByUsername(@PathParam("username") final String userName) {
        try {
            try {
                return Response.status(Response.Status.OK)
                        .entity(userService.findByUsername(userName))
                        .build();
            } catch (SQLException | InterruptedException e) {
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity("e.getMessage()")
                        .build();
            }
        } catch (NullPointerException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("e.getMessage()")
                    .build();
        }
    }

    /**
     * Сохраняет запись пользователя
     *
     * @param userCreateRequest пользователь, который должен быть сохранён в базе данных
     * @return возвращает статус сохранился ли пользователь или нет
     */
    @POST
    public Response save(@Valid final UserCreateRequest userCreateRequest) {

        try {
            userService.save(userCreateRequest);
            return Response.status(Response.Status.CREATED)
                    .entity(userCreateRequest)
                    .build();
        } catch (SQLException | InterruptedException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Сохраняет запись пользователя
     *
     * @param userUpdateRequest пользователь, который должен быть сохранён в базе данных
     * @return возвращает статус сохранился ли пользователь или нет
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") final int id, @Valid final UserUpdateRequest userUpdateRequest) {

        try {
            userService.update(id, userUpdateRequest);
            return Response.status(Response.Status.CREATED)
                    .entity(userUpdateRequest)
                    .build();
        } catch (SQLException | InterruptedException e) {
            return Response.status((Response.Status.BAD_REQUEST))
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Удаляет запись пользователя по его идентификатору
     *
     * @param id идентификатор пользователя
     * @return возвращает статус удаления пользователя
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final int id) {
        try {
            return Response.status(Response.Status.OK)
                    .entity(userService.delete(id))
                    .build();
        } catch (SQLException | InterruptedException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("e.getMessage()")
                    .build();
        }
    }
}
