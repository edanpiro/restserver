package com.idat.restserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idat.restserver.entity.Person;

import com.idat.restserver.repository.PersonRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Path("/person")
public class PersonController {
    @Autowired
    // private static List<Person> persons = new ArrayList<>();
    private PersonRepository personRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /*static {
        persons.add(new Person("Juan", 20));
        persons.add(new Person("Pedro", 30));
    }*/

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson() {
        try {
            List<Person> persons = personRepository.findAll();
            String json = objectMapper.writeValueAsString(persons);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al convertir a JSON")
                    .build();
        }
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonByName(@PathParam("name") String name) {
        Person person = personRepository.findByName(name);
        if (person != null) {
            try {
                String json = objectMapper.writeValueAsString(person);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } catch (JsonProcessingException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al convertir a JSON")
                        .build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\":\"Persona no encontrada\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") Long id, String json) {
        try {
            Person updatePerson = objectMapper.readValue(json, Person.class);
            Person person = personRepository.findById(id).orElse(null);
            if (person != null) {
                person.setName(updatePerson.getName());
                person.setAge(updatePerson.getAge());
                personRepository.save(person);
                String responseMessage = "{\"message\":\"Persona actualizada correctamente\"}";
                return Response.status(Response.Status.OK)
                        .entity(responseMessage)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") Long id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person != null) {
            personRepository.delete(person);
            String responseMessage = "{\"message\":\"Persona eliminada correctamente\"}";
            return Response.status(Response.Status.NO_CONTENT)
                    .entity(responseMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\":\"Persona no encontrada\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(String json) {
        try {
            Person newPerson = objectMapper.readValue(json, Person.class);
            personRepository.save(newPerson);
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
