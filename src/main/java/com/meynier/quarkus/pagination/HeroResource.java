package com.meynier.quarkus.pagination;

import io.quarkus.panache.common.Page;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hero")
@Produces(MediaType.APPLICATION_JSON)
public class HeroResource {

    @GET
    public Response findAll(@Context Page page) {
        return Response.ok().entity(Hero.findAll().page(page).list()).build();
    }
}