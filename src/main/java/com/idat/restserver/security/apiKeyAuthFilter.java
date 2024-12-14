package com.idat.restserver.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class apiKeyAuthFilter implements ContainerRequestFilter {

    private static final String SUBSCRIPTION_KEY_HEADER = "Subscription-key";
    private static final String VALID_API_KEY = "123e4567-e89b-12d3-a456-426614174000"; // Reemplaza con tu ApiKey válida

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String apiKey = requestContext.getHeaderString(SUBSCRIPTION_KEY_HEADER);
        if (apiKey == null || !isValidApiKey(apiKey)) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isValidApiKey(String apiKey) {
        return VALID_API_KEY.equals(apiKey);
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("ApiKey no autorizada")
                .build());
    }
}