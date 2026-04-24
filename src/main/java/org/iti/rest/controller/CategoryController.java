package org.iti.rest.controller;

import org.iti.rest.dto.CategoryResponse;
import org.iti.rest.dto.CreateCategoryRequest;
import org.iti.rest.dto.UpdateCategoryRequest;
import org.iti.rest.factory.ServiceFactory;
import org.iti.rest.service.CategoryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/categories")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController() {
        this.categoryService = ServiceFactory.getCategoryService();
    }

    @GET
    public Response getAllCategories() {
        List<CategoryResponse> categories = categoryService.findAll();
        return Response.ok(categories).build();
    }

    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") Short id) {
        try {
            CategoryResponse category = categoryService.findById(id);
            return Response.ok(category).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    public Response createCategory(CreateCategoryRequest request) {
        try {
            CategoryResponse created = categoryService.create(request);
            return Response
                    .created(URI.create("/api/categories/" + created.getCategoryId()))
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
    public Response updateCategory(@PathParam("id") Short id, UpdateCategoryRequest request) {
        try {
            request.setCategoryId(id);
            CategoryResponse updated = categoryService.update(request);
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
    public Response deleteCategory(@PathParam("id") Short id) {
        try {
            boolean deleted = categoryService.deleteById(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Category not found\"}")
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