package ru.vinhome.controller;
//
//import jakarta.inject.Inject;
//import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.DELETE;
//import jakarta.ws.rs.GET;
//import jakarta.ws.rs.POST;
//import jakarta.ws.rs.PUT;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.PathParam;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import ru.vinhome.controller.dto.MessageCreateRequest;
//import ru.vinhome.controller.dto.MessageUpdateRequest;
//import ru.vinhome.model.Message;
//import ru.vinhome.repository.UserRepository;
//import ru.vinhome.service.MessageService;
//
//import java.sql.SQLException;
//import java.util.Optional;
//
//@Path("/v1/messages")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class MessageRestController {
//
//    private final MessageService messageService;
//
//    @Inject
//    public MessageRestController(MessageService messageService) {
//        this.messageService = messageService;
//    }
//
//    /**
//     * Выдаёт все записи сообщений
//     *
//     * @return возвращает http ответ с сообщениями в формате JSON
//     */
//    @GET
//    public Response findAll() {
//        try {
//            return Response.status(Response.Status.OK)
//                    .entity(messageService.findAll())
//                    .build();
//        } catch (SQLException e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity(e.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * Выдаёт запись сообщения по её идентификатору
//     *
//     * @return возвращает http ответ с сообщением в формате JSON, в случае неудачного ответа выдаёт
//     * сообщение об ошибке
//     */
//    @GET
//    @Path("/{id}")
//    public Response findById(@PathParam("id") final Integer id) {
//        try {
//
//            Optional<Message> mayBeMessage = messageService.findById(id);
//
//            return mayBeMessage.isPresent()
//                    ? Response.status(Response.Status.OK)
//                                            .entity(mayBeMessage.get())
//                                            .build()
//                    : Response.status(Response.Status.NOT_FOUND)
//                                            .entity("Сообщение не найдено")
//                                            .build();
//        } catch (SQLException e) {
//            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
//        }
//    }
//
//    /**
//     * Сохраняет запись сообщения в базе данных, принимая данные в формате JSON
//     *
//     * @return возвращает http ответ с сообщением в формате JSON, в случае неудачного ответа выдаёт
//     * сообщение об ошибке
//     */
//    @POST
//    public Response save(MessageCreateRequest message) {
//
//        try {
//            if (UserRepository.userExistByID(message.recipient()) && UserRepository.userExistByID(message.sender())) {
//                messageService.save(message);
//                return Response.status(Response.Status.OK)
//                        .entity(message)
//                        .build();
//            } else {
//                return Response.status(Response.Status.BAD_REQUEST)
//                        .entity("Получателя или отправителя не существует, необходимо исправить сообщение")
//                        .build();
//            }
//
//        } catch (SQLException e) {
//            return Response.status(Response.Status.NOT_MODIFIED)
//                    .entity(e.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * Изменяет запись сообщения в базе данных
//     *
//     * @param id      идентификатор сообщения которое должно быть изменено в базе данных
//     * @param message тело сообщения изменяемого сообщения
//     * @return возвращает статус сохранился ли пользователь или нет
//     */
//    @PUT
//    @Path("/{id}")
//    public Response update(@PathParam("id") final Integer id, final MessageUpdateRequest message) {
//
//        try {
//            messageService.update(id, message);
//            return Response.status(Response.Status.CREATED)
//                    .entity(message)
//                    .build();
//        } catch (SQLException e) {
//            return Response.status((Response.Status.NOT_MODIFIED))
//                    .entity(e.getMessage())
//                    .build();
//        }
//    }
//
//    /**
//     * Удаляет запись сообщения по её идентификатору
//     *
//     * @param id идентификатор сообщения
//     * @return возвращает статус удаления сообщения
//     */
//    @DELETE
//    @Path("/{id}")
//    public Response delete(@PathParam("id") final int id) {
//        try {
//            return Response.status(Response.Status.OK)
//                    .entity(messageService.delete(id))
//                    .build();
//        } catch (SQLException e) {
//            return Response
//                    .status(Response.Status.NOT_MODIFIED)
//                    .entity("e.getMessage()")
//                    .build();
//        }
//    }
//}
