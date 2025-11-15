package ru.vinhome.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/v1/test")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class TestRestController {

    @GET
    public Response get() {
        return Response
                .status(Response.Status.OK)
                .entity("TEST")
                .build();
    }

}
