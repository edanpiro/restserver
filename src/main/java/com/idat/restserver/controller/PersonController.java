package com.idat.restserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idat.restserver.entity.Person;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Path("/person")
public class PersonController {
    private static List<Person> persons = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        persons.add(new Person("Juan", 20));
        persons.add(new Person("Pedro", 30));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson() {
        try {
            String json = objectMapper.writeValueAsString(persons);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al convertir a JSON")
                    .build();
        }
    }

    @PUT
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("name") String name, String json) {
        try {
            Person updatePerson = objectMapper.readValue(json, Person.class);
            for (Person person : persons) {
                if (person.getName().equals(name)) {
                    person.setName(updatePerson.getName());
                    person.setEdad(updatePerson.getEdad());
                    return Response.ok(person).build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud")
                    .build();
        }
    }

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("name") String name) {
        for (Person person : persons) {
            if (person.getName().equals(name)) {
                persons.remove(person);
                return Response.noContent().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Persona no encontrada")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(String json) {
        try {
            Person newPerson = objectMapper.readValue(json, Person.class);
            persons.add(newPerson);
            String createdJson = objectMapper.writeValueAsString(newPerson);
            return Response.status(Response.Status.CREATED)
                    .entity(createdJson)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud")
                    .build();
        }
    }
}
