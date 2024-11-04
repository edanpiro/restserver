package com.idat.restserver.config;

import com.idat.restserver.controller.PersonController;
import com.idat.restserver.controller.ProductController;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig extends ResourceConfig {
    public ServerConfig() {
        register(PersonController.class);
        register(ProductController.class);
    }
}
