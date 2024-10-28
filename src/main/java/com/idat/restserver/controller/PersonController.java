package com.idat.restserver.controller;

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
    static {
        persons.add(new Person("Juan", 20));
        persons.add(new Person("Pedro", 30));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPerson() {
        return persons;
    }

    @PUT
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("name") String name, Person updatePerson) {
        for (Person person : persons) {
            if (person.getName().equals(name)) {
                person.setName(updatePerson.getName());
                person.setEdad(updatePerson.getEdad());
                return Response.ok(person).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
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
    public Response addPerson(Person newPerson) {
        persons.add(newPerson);
        return Response.status(Response.Status.CREATED).entity(newPerson).build();
    }
}
