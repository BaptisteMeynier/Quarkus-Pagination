package com.meynier.quarkus.pagination;

import io.quarkus.panache.common.Sort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortBuilderTest {


    @Test
    public void it_should_throw_exception() {
        //GIVEN
        String parameter = ",,,,";
        //WHEN
        Sort sort = SortBuilder.build(parameter);
        //THEN
    }

    @Test
    public void it_should_parse_simple_sort() {
        //GIVEN
        String parameter = "field";
        //WHEN
        Sort sort = SortBuilder.build(parameter);
        //THEN
        List<Sort.Column> columns = sort.getColumns();
        assertEquals(1, columns.size());
        assertEquals(parameter, columns.get(0).getName());
    }

    @Test
    public void it_should_be_asc_default_direction() {
        //GIVEN
        String parameter = "field";
        //WHEN
        Sort sort = SortBuilder.build(parameter);
        //THEN
        Sort.Column column = sort.getColumns().get(0);
        assertEquals(Sort.Direction.Ascending, column.getDirection());

    }

    @Test
    public void it_should_parse_sort_with_asc_direction() {
        //GIVEN
        String parameter = "field:asc";
        //WHEN
        Sort sort = SortBuilder.build(parameter);
        //THEN
        Sort.Column column = sort.getColumns().get(0);
        assertEquals(Sort.Direction.Ascending, column.getDirection());
    }

    @Test
    public void it_should_parse_sort_with_desc_direction() {
        //GIVEN
        String parameter = "field:desc";
        //WHEN
        Sort sort = SortBuilder.build(parameter);
        //THEN
        Sort.Column column = sort.getColumns().get(0);
        assertEquals(Sort.Direction.Descending, column.getDirection());
    }

    @Test
    public void it_should_parse_multiple_sort() {
        //GIVEN
        String parameter = "field,field2";
        //WHEN
        Sort sort = SortBuilder.build(parameter);
        //THEN
        int size = sort.getColumns().size();
        assertEquals(2, size);
    }

    @Test
    public void it_should_parse_multiple_sort_with_direction() {
        //GIVEN
        String parameter = "field:desc,field2:asc";
        //WHEN
        Sort sort = SortBuilder.build(parameter);
        //THEN
        Sort.Column column1 = sort.getColumns().get(0);
        Sort.Column column2 = sort.getColumns().get(1);
        assertEquals(Sort.Direction.Descending, column1.getDirection());
        assertEquals(Sort.Direction.Ascending, column2.getDirection());
    }

    @Test
    public void it_should_parse_multiple_sort_with_heterogeneous_direction() {
        //GIVEN
        String parameter = "field,field2:desc";
        //WHEN
        Sort sort = SortBuilder.build(parameter);
        //THEN
        Sort.Column column1 = sort.getColumns().get(0);
        Sort.Column column2 = sort.getColumns().get(1);
        assertEquals(Sort.Direction.Ascending, column1.getDirection());
        assertEquals(Sort.Direction.Descending, column2.getDirection());
    }

}