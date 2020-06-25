package com.meynier.quarkus.pagination;

import javax.ws.rs.core.UriInfo;
import java.util.List;

public class Paginated<T> {

    private List<T> entities;
    private int currentPage;
    private int perPage;
    private int pageCount;
    private int totalCount;

    private Paginated() {
    }

    private Paginated(List<T> entities, int currentPage, int perPage, int pageCount, int totalCount) {
        this.entities = entities;
        this.currentPage = currentPage;
        this.perPage = perPage;
        this.pageCount = pageCount;
        this.totalCount = totalCount;
    }

    public static <T> CurrentPageStep entities(List<T>  entities) {
        return new Paginated.Builder(entities);
    }

    public interface CurrentPageStep {
        PerPageStep currentPage(int perPage);
    }

    public interface PerPageStep {
        TotalCountStep perPage(int perPage);
    }

    public interface TotalCountStep {
        EndStep totalCount(int totalCount);
    }

    public interface EndStep {
        Paginated build();
    }

    public static class Builder<T> implements CurrentPageStep, PerPageStep, TotalCountStep, EndStep{

        private List<T> entities;
        private int currentPage;
        private int perPage;
        private int totalCount;

        Builder(List<T> entities) {
            this.entities = entities;
        }

        @Override
        public PerPageStep currentPage(int currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        @Override
        public TotalCountStep perPage(int perPage) {
            this.perPage = perPage;
            return this;
        }

        @Override
        public EndStep totalCount(int totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        @Override
        public Paginated build() {
            int pageCount = (int) this.totalCount / this.perPage;
            return new Paginated(this.entities,this.currentPage, this.perPage, pageCount ,this.totalCount);
        }
    }


    public List<T> getEntities() {
        return entities;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}