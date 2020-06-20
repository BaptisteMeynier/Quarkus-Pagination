package com.meynier.quarkus.pagination;

import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.spi.ContextInjector;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class PageInjector implements ContextInjector<Uni<Page>, Page> {

    @Context
    UriInfo uriInfo;

    @ConfigProperty(name = "quarkus.pagination.default-size")
    private int size;

    @Override
    public Uni<Page> resolve(Class<? extends Uni<Page>> rawType, Type genericType, Annotation[] annotations) {
        return Uni.createFrom().item(Page.of(getIndex(), getSize()));
    }

    private int getIndex() {
        MultivaluedMap<String, String> params = uriInfo.getQueryParameters(true);
        if (params.containsKey("page")) {
            return Integer.parseInt(params.getFirst("page"));
        }
        return 0;
    }

    private int getSize() {
        MultivaluedMap<String, String> params = uriInfo.getQueryParameters(true);
        if (params.containsKey("size")) {
            return Integer.parseInt(params.getFirst("size"));
        }
        return size;
    }
}
