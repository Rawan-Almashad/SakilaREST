package org.iti.rest.controller;

import org.iti.rest.dto.CreateCustomerRequest;
import org.iti.rest.dto.CustomerResponse;
import org.iti.rest.dto.UpdateCustomerRequest;
import org.iti.rest.factory.ServiceFactory;
import org.iti.rest.service.CustomerService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/customers")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController() {
        this.customerService = ServiceFactory.getCustomerService();
    }

    /**
     * GET /api/customers - Get all customers
     */
    @GET
    public Response getAllCustomers() {
        List<CustomerResponse> customers = customerService.findAll();
        return Response.ok(customers).build();
    }

    /**
     * GET /api/customers/{id} - Get customer by ID
     */
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Short id) {
        try {
            CustomerResponse customer = customerService.findById(id);
            return Response.ok(customer).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * POST /api/customers - Create a new customer
     */
    @POST
    public Response createCustomer(CreateCustomerRequest request) {
        try {
            CustomerResponse created = customerService.create(request);
            return Response
                    .created(URI.create("/api/customers/" + created.getId()))
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
     * PUT /api/customers/{id} - Update a customer
     */
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") Short id, UpdateCustomerRequest request) {
        try {
            request.setId(id);
            CustomerResponse updated = customerService.update(request);
            return Response.ok(updated).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * DELETE /api/customers/{id} - Delete a customer
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Short id) {
        try {
            boolean deleted = customerService.deleteById(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Customer not found\"}")
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