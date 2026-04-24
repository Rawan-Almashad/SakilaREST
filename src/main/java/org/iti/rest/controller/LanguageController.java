package org.iti.rest.controller;

import org.iti.rest.dto.CreateLanguageRequest;
import org.iti.rest.dto.LanguageResponse;
import org.iti.rest.dto.UpdateLanguageRequest;
import org.iti.rest.factory.ServiceFactory;
import org.iti.rest.service.LanguageService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/languages")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController() {
        this.languageService = ServiceFactory.getLanguageService();
    }

    @GET
    public Response getAllLanguages() {
        List<LanguageResponse> languages = languageService.getAll();
        return Response.ok(languages).build();
    }

    @GET
    @Path("/{id}")
    public Response getLanguageById(@PathParam("id") Short id) {
        try {
            LanguageResponse language = languageService.getById(id);
            return Response.ok(language).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    public Response createLanguage(CreateLanguageRequest request) {
        try {
            LanguageResponse created = languageService.create(request);
            return Response
                    .created(URI.create("/api/languages/" + created.getId()))
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateLanguage(@PathParam("id") Short id, UpdateLanguageRequest request) {
        try {
            request.setId(id);
            LanguageResponse updated = languageService.update(request);
            return Response.ok(updated).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * DELETE /api/languages/{id} - Delete a language
     */
    @DELETE
    @Path("/{id}")
    public Response deleteLanguage(@PathParam("id") Short id) {
        try {
            boolean deleted = languageService.delete(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Language not found\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}