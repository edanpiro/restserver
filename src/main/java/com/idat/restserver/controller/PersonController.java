package com.idat.restserver.controller;

import com.idat.restserver.entity.Person;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.MediaType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Path("/person")
public class PersonController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPerson() {
        return Arrays.asList(
                new Person("Juan", 20),
                new Person("Pedro", 30)
        );
    }
}
