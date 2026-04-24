package org.iti.rest.controller;

import org.iti.rest.dto.AddActorToFilmRequest;
import org.iti.rest.dto.CreateFilmRequest;
import org.iti.rest.dto.ReturnFilm;
import org.iti.rest.dto.ReturnFilmWithLanguage;
import org.iti.rest.entity.Film;
import org.iti.rest.factory.ServiceFactory;
import org.iti.rest.service.FilmService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/films")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class FilmController {

    private final FilmService filmService;

    public FilmController() {
        this.filmService = ServiceFactory.getFilmService();
    }

    @GET
    public Response getAllFilms() {
      List<ReturnFilmWithLanguage> films = filmService.findAll();
      return Response.ok(films).build();
    }

    @GET
    @Path("/{id}")
    public Response getFilmById(@PathParam("id") Short id) {
        try{
            ReturnFilm film =filmService.findById(id);
            return Response.ok(film).build();
        }
        catch (RuntimeException e)
        {
            return Response.status(Response.Status.NOT_FOUND).
                    entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }


    @GET
    @Path("/{id}/details")
    public Response getFilmWithLanguage(@PathParam("id") Short id) {
        try {
            ReturnFilmWithLanguage film = filmService.findWithLanguage(id);
            return Response.ok(film).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    public Response createFilm(CreateFilmRequest film) {
        try {
            ReturnFilm created = filmService.create(film);
            return Response
                    .created(URI.create("/api/films/" + created.getId()))
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
    public Response deleteFilm(@PathParam("id") Short id) {
        try {
            filmService.delete(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    @Path("/{id}/actors")
    public Response addActorToFilm(@PathParam("id") Short filmId, AddActorToFilmRequest request) {
        try {
            // Set the film ID from the path parameter
            request.setFilmId(filmId);
            filmService.addActorToFilm(request);

            return Response
                    .created(URI.create("/api/films/" + filmId + "/actors/" + request.getActorId()))
                    .entity("{\"message\": \"Actor added to film successfully\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}