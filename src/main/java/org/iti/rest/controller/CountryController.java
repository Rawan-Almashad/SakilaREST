package org.iti.rest.controller;

import org.iti.rest.dto.CreateCountry;
import org.iti.rest.dto.UpdateCountryRequest;
import org.iti.rest.factory.ServiceFactory;
import org.iti.rest.service.CountryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

import static jakarta.ws.rs.core.Response.ok;

@Path("/countries")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CountryController {

    private final CountryService countryService;

    public CountryController() {
        this.countryService = ServiceFactory.getCountryService();
    }

    /**
     * GET /api/countries - Get all countries
     */
    @GET
    public Response getAllCountries() {
        List<CreateCountry> countries = countryService.findAll();
        return ok(countries).build();
    }

    /**
     * GET /api/countries/{id} - Get country by ID
     */
    @GET
    @Path("/{id}")
    public Response getCountryById(@PathParam("id") Short id) {
        try {
            CreateCountry country = countryService.findById(id);
            return ok(country).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * POST /api/countries - Create a new country
     */
    @POST
    public Response createCountry(UpdateCountryRequest request) {
        try {
            CreateCountry request2= new CreateCountry();
            request2.setCountry(request.getCountry());
            CreateCountry created = countryService.save(request2);
            return Response.ok()
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
     * PUT /api/countries/{id} - Update a country
     */
    @PUT
    @Path("/{id}")
    public Response updateCountry(@PathParam("id") Short id, UpdateCountryRequest request) {
        try {
            request.setId(id);
            CreateCountry updated = countryService.update(request);
            return ok(updated).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * DELETE /api/countries/{id} - Delete a country
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCountry(@PathParam("id") Short id) {
        try {
            boolean deleted = countryService.deleteById(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Country not found\"}")
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