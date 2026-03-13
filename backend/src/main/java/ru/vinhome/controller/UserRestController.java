package ru.vinhome.controller;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vinhome.controller.dto.UserCreateRequest;
import ru.vinhome.model.User;
import ru.vinhome.service.UserService;

import java.sql.SQLException;
import java.util.List;

/**
 * Класс контроллер пользователя
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Выдаёт все записи пользователей
     *
     * @return возвращает http ответ с данными о пользователях в формате JSON
     */
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        try {
            return ResponseEntity.ok(userService.findAll());
        } catch (SQLException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Выдаёт запись пользователя по его идентификатору
     *
     * @param id идентификатор пользователя
     * @return возвращает объект класса User
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") final int id) {
        try {
            final var maybeUser = userService.findById(id);

            return maybeUser.isPresent()
                    ? ResponseEntity.ok(maybeUser.get())
                    : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Выдаёт запись пользователя по его имени пользователя
     *
     * @param userName имя пользователя
     * @return возвращает объект класса User
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable("username") final String userName) {
        try {
            return ResponseEntity.ok(userService.findByUsername(userName));
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    /**
     * Сохраняет запись пользователя
     *
     * @param userCreateRequest пользователь, который должен быть сохранён в базе данных
     * @return возвращает статус сохранился ли пользователь или нет
     */
    @PostMapping
    public ResponseEntity<UserCreateRequest> save(@RequestBody final UserCreateRequest userCreateRequest) {
        try {
            userService.save(userCreateRequest);
            return ResponseEntity.status(201).body(userCreateRequest);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

//    /**
//     * Сохраняет запись пользователя
//     *
//     * @param userUpdateRequest пользователь, который должен быть сохранён в базе данных
//     * @return возвращает статус сохранился ли пользователь или нет
//     */
//    @PUT
//    @Path("/{id}")
//    public Response update(@PathParam("id") final int id, @Valid final UserUpdateRequest userUpdateRequest) {
//
//        try {
//            userService.update(id, userUpdateRequest);
//            return Response.status(Response.Status.CREATED)
//                    .entity(userUpdateRequest)
//                    .build();
//        } catch (SQLException e) {
//            return Response.status((Response.Status.NOT_MODIFIED))
//                    .entity(e.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * Удаляет запись пользователя по его идентификатору
//     *
//     * @param id идентификатор пользователя
//     * @return возвращает статус удаления пользователя
//     */
//    @DELETE
//    @Path("/{id}")
//    public Response delete(@PathParam("id") final int id) {
//        try {
//            return Response.status(Response.Status.OK)
//                    .entity(userService.delete(id))
//                    .build();
//        } catch (SQLException e) {
//            return Response
//                    .status(Response.Status.NOT_MODIFIED)
//                    .entity("e.getMessage()")
//                    .build();
//        }
//    }
}
