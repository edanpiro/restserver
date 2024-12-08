package com.idat.restserver.config;

import com.idat.restserver.controller.PersonController;
import com.idat.restserver.controller.ProductController;

import com.idat.restserver.security.BasicAuthFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig extends ResourceConfig {
    public ServerConfig() {
        register(BasicAuthFilter.class);
        register(PersonController.class);
        register(ProductController.class);
    }
}
