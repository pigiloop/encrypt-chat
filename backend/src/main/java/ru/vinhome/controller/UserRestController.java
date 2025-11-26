package ru.vinhome.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.service.UserServiceImpl;

import java.sql.SQLException;

@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRestController {

    private static final UserServiceImpl userService = new UserServiceImpl(new JdbcUserRepositoryImpl());

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

    @POST
    public Response save(final User user) {

        if (user.getUserName().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User is empty, set data in user")
                    .build();
        }
        try {
            if (userService.emailExist(user.getEmail())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("This email exist, please enter other email")
                        .build();
            }
            userService.save(user);

            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .build();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") final long id) {
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

}
