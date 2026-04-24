package org.iti.rest.controller;

import org.iti.rest.dto.CreateStoreRequest;
import org.iti.rest.dto.StoreResponse;
import org.iti.rest.dto.UpdateStoreRequest;
import org.iti.rest.factory.ServiceFactory;
import org.iti.rest.service.StoreService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/stores")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StoreController {

    private final StoreService storeService;

    public StoreController() {
        this.storeService = ServiceFactory.getStoreService();
    }

    @GET
    public Response getAllStores() {
        List<StoreResponse> stores = storeService.findAll();
        return Response.ok(stores).build();
    }

    @GET
    @Path("/{id}")
    public Response getStoreById(@PathParam("id") Short id) {
        try {
            StoreResponse store = storeService.findById(id);
            return Response.ok(store).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    public Response createStore(CreateStoreRequest request) {
        try {
            StoreResponse created = storeService.create(request);
            return Response
                    .created(URI.create("/api/stores/" + created.getStoreId()))
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
    public Response updateStore(@PathParam("id") Short id, UpdateStoreRequest request) {
        try {
            request.setStoreId(id);
            StoreResponse updated = storeService.update(request);
            return Response.ok(updated).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStore(@PathParam("id") Short id) {
        try {
            boolean deleted = storeService.deleteById(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Store not found\"}")
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