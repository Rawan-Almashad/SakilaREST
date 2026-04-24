package org.iti.rest.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApplication extends Application {
    // Empty - Jersey auto-discovers all @Path annotations
    // This makes all REST endpoints available under /api/*
}