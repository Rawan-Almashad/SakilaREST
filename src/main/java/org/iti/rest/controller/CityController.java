package org.iti.rest.controller;

import org.iti.rest.dto.CityResponse;
import org.iti.rest.dto.CreateCityRequest;
import org.iti.rest.dto.UpdateCityRequest;
import org.iti.rest.factory.ServiceFactory;
import org.iti.rest.service.CityService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/cities")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CityController {

    private final CityService cityService;

    public CityController() {
        this.cityService = ServiceFactory.getCityService();
    }

    /**
     * GET /api/cities - Get all cities
     */
    @GET
    public Response getAllCities() {
        List<CityResponse> cities = cityService.findAll();
        return Response.ok(cities).build();
    }

    /**
     * GET /api/cities/{id} - Get city by ID
     */
    @GET
    @Path("/{id}")
    public Response getCityById(@PathParam("id") Short id) {
        try {
            CityResponse city = cityService.findById(id);
            return Response.ok(city).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * POST /api/cities - Create a new city
     */
    @POST
    public Response createCity(CreateCityRequest request) {
        try {
            CityResponse created = cityService.save(request);
            return Response
                    .created(URI.create("/api/cities/" + created.getId()))
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * PUT /api/cities/{id} - Update a city
     */
    @PUT
    @Path("/{id}")
    public Response updateCity(@PathParam("id") Short id, UpdateCityRequest request) {
        try {
            request.setId(id);
            CityResponse updated = cityService.update(request);
            return Response.ok(updated).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * DELETE /api/cities/{id} - Delete a city
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCity(@PathParam("id") Short id) {
        try {
            boolean deleted = cityService.deleteById(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"City not found\"}")
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