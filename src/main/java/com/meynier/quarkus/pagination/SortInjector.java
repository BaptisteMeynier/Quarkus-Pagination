package com.meynier.quarkus.pagination;

import io.quarkus.panache.common.Sort;
import org.jboss.resteasy.spi.ContextInjector;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

@Provider
public class SortInjector implements ContextInjector<Sort, List<Sort.Column>> {

    @Context
    UriInfo uriInfo;

    @Override
    public Sort resolve(Class<? extends Sort> aClass, Type type, Annotation[] annotations) {
        return build();
    }

    private Sort build() {
        MultivaluedMap<String, String> params = uriInfo.getQueryParameters(true);
        if (params.containsKey("sort")) {
            return SortBuilder.build(params.getFirst("sort"));
        }
        return null;
    }
}
