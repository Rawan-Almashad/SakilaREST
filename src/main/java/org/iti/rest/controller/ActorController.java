package org.iti.rest.controller;

import org.iti.rest.dto.CreateActorRequest;
import org.iti.rest.entity.Actor;
import org.iti.rest.factory.ServiceFactory;
import org.iti.rest.service.ActorService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/actors")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ActorController {

    private final ActorService actorService;

    public ActorController() {
        this.actorService = ServiceFactory.getActorService();
    }

    @GET
    public Response getAllActors() {
        List<Actor> actors = actorService.findAll();
        return Response.ok(actors).build();
    }

    @GET
    @Path("/{id}")
    public Response getActorById(@PathParam("id") Short id) {
        try {
            Actor actor = actorService.findById(id);
            return Response.ok(actor).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    public Response createActor(CreateActorRequest request) {
        try {
            Actor created = actorService.create(request);
            return Response
                    .created(URI.create("/api/actors/" + created.getId()))
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteActor(@PathParam("id") Short id) {
        try {
            actorService.delete(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}