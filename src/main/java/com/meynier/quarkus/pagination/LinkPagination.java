package com.meynier.quarkus.pagination;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import java.util.stream.Stream;

import static com.meynier.quarkus.pagination.PaginationConstants.*;

public class LinkPagination {

    private LinkPagination() {
    }

    public static PageSizeStep uriInfo(UriInfo uriInfo) {
        return new Builder(uriInfo);
    }

    public interface PageSizeStep {
        PageNumberStep pageSize(int defaultPageSize);
    }

    public interface PageNumberStep {
        TotalEntityCountStep pageNumber(int pageNumber);
    }

    public interface TotalEntityCountStep {
        TotalPageCountStep totalEntityCount(int totalEntityCount);
    }

    public interface TotalPageCountStep {
        EndStep totalPageCount(int totalEntityCount);
    }

    public interface EndStep {
        Stream<Link> build();
    }

    public static class Builder implements PageSizeStep, PageNumberStep, TotalEntityCountStep, TotalPageCountStep,EndStep {
        private UriInfo uriInfo;
        private int pageSize;
        private int pageNumber;
        private int totalEntityCount;
        private int totalPageCount;

        private Builder(UriInfo uriInfo) {
            this.uriInfo=uriInfo;
        }


        @Override
        public PageNumberStep pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        @Override
        public TotalEntityCountStep pageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        @Override
        public TotalPageCountStep totalEntityCount(int totalEntityCount) {
            this.totalEntityCount = totalEntityCount;
            return this;
        }

        @Override
        public EndStep totalPageCount(int totalPageCount) {
            this.totalPageCount = totalPageCount;
            return this;
        }


        private Stream<Link> toLinks(UriInfo uriInfo, int pageSize, int pageNumber, int totalEntityCount, int totalPageCount) {
            if (pageNumber == 1 && totalPageCount == 1) {
                return Stream.empty();
            }

            Stream.Builder<Link> linkStreamBuilder = Stream.builder();

            if (pageNumber > 1) {
                linkStreamBuilder.accept(
                        Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                .replaceQueryParam(PAGE_QUERY_PARAM, pageNumber - 1))
                                .rel(PREV_REL)
                                .build());
            }

            if (pageNumber < totalPageCount) {
                linkStreamBuilder.accept(
                        Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                .replaceQueryParam(PAGE_QUERY_PARAM, pageNumber + 1))
                                .rel(NEXT_REL)
                                .build());
            }

            linkStreamBuilder.accept(
                    Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                            .replaceQueryParam(PAGE_QUERY_PARAM, FIRST_PAGE))
                            .rel(FIRST_REL)
                            .build());

            linkStreamBuilder.accept(
                    Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                            .replaceQueryParam(PAGE_QUERY_PARAM, totalPageCount))
                            .rel(LAST_REL)
                            .build());

            return linkStreamBuilder.build();
        }

        @Override
        public Stream<Link> build() {
            return toLinks(this.uriInfo,this.pageSize, this.pageNumber, this.totalEntityCount, this.totalPageCount);
        }
    }

}