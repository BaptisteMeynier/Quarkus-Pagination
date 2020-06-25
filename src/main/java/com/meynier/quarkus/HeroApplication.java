package com.meynier.quarkus;

import com.meynier.quarkus.pagination.LinkPaginationContainerResponseFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest/v1")
public class HeroApplication extends Application {

    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>(2);
        classes.add(HeroResource.class);
        classes.add(LinkPaginationContainerResponseFilter.class);
        return classes;
    }
}
