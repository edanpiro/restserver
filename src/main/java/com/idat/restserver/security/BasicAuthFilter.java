package com.idat.restserver.security;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;

import java.util.Base64;
import java.io.IOException;
import java.util.StringTokenizer;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(AUTHORIZATION_HEADER);
        if (authHeader != null && !authHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        String base64Credentials = authHeader.substring(AUTHORIZATION_HEADER_PREFIX.length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        StringTokenizer tokenizer = new StringTokenizer(credentials, ":");
        String username = tokenizer.nextToken();
        String password = tokenizer.nextToken();

        if (!isValidateUser(username, password)) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isValidateUser(String username, String password) {
        return "admin".equals(username) && "admin12345".equals(password);
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("Usuario no autorizado")
                .build());
    }
}
