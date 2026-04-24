package org.iti.rest.controller;

import org.iti.rest.dto.CreateStaffRequest;
import org.iti.rest.dto.StaffResponse;
import org.iti.rest.dto.UpdateStaffRequest;
import org.iti.rest.factory.ServiceFactory;
import org.iti.rest.service.StaffService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/staff")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class StaffController {

    private final StaffService staffService;

    public StaffController() {
        this.staffService = ServiceFactory.getStaffService();
    }

    @GET
    public Response getAllStaff() {
        List<StaffResponse> staff = staffService.findAll();
        return Response.ok(staff).build();
    }

    @GET
    @Path("/{id}")
    public Response getStaffById(@PathParam("id") Byte id) {
        try {
            StaffResponse staff = staffService.findById(id);
            return Response.ok(staff).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    public Response createStaff(CreateStaffRequest request) {
        try {
            StaffResponse created = staffService.create(request);
            return Response
                    .created(URI.create("/api/staff/" + created.getId()))
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
    public Response updateStaff(@PathParam("id") Byte id, UpdateStaffRequest request) {
        try {
            request.setId(id);
            StaffResponse updated = staffService.update(request);
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
    public Response deleteStaff(@PathParam("id") Byte id) {
        try {
            boolean deleted = staffService.deleteById(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Staff not found\"}")
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