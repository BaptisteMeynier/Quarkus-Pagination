package com.meynier.quarkus;

import com.meynier.quarkus.pagination.Paginated;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/hero")
@Produces(MediaType.APPLICATION_JSON)
public class HeroResource {

    @Context
    Page page;

    @Context
    Sort sort;

    @GET
    public Response findAll() {
        Response response = Response.status(NOT_FOUND).build();
        final List<PanacheEntityBase> heroes = Hero.findAll(sort).page(page).list();
        if (!heroes.isEmpty()) {
            int totalHeroes = (int) Hero.findAll().count();
            response = Response.ok()
                    .entity(Paginated.entities(heroes).currentPage(page.index).perPage(page.size).totalCount(totalHeroes).build())
                    .build();
        }
        return response;
    }
}