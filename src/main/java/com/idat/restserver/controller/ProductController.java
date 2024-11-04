package com.idat.restserver.controller;

import com.idat.restserver.entity.Product;

import com.idat.restserver.entity.ProductList;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Path("/product")
public class ProductController {
    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product("Laptop", 2000, 10));
        products.add(new Product("Mouse", 50, 100));
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response listProducts() {
        return Response
                .status(Response.Status.OK)
                        .entity(new ProductList(products)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response addProduct(Product product) {
        products.add(product);
        return Response.status(Response.Status.CREATED)
                .entity(product).build();
    }

    @PUT
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateProduct(@PathParam("name") String name, Product product) {
        for (Product p : products) {
            if (p.getName().equals(name)) {
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setStock(product.getStock());
                return Response.status(Response.Status.OK)
                        .entity(p).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("Producto no encontrado").build();
    }

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteProduct(@PathParam("name") String name) {
        for (Product p : products) {
            if (p.getName().equals(name)) {
                products.remove(p);
                return Response.ok(p).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Producto no encontrado").build();
    }
}
