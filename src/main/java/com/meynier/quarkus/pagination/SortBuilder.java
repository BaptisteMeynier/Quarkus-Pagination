package com.meynier.quarkus.pagination;

import io.quarkus.panache.common.Sort;


public class SortBuilder {

    private final static Sort.Direction DEFAULT_DIRECTION = Sort.Direction.Ascending;

    public static Sort build(String sortParam) {
        String[] sortInstructions = sortParam.split(",");
        Sort init = null;
        if (sortInstructions.length > 0) {
            init = Sort.by(getField(sortInstructions[0]), getDirection(sortInstructions[0]));
            for (int index = 1; index < sortInstructions.length; index++) {
                init.and(getField(sortInstructions[index]), getDirection(sortInstructions[index]));
            }
        }
        return init;
    }

    private static String getField(String sortInstruction) {
        return sortInstruction.split(":")[0];
    }


    private static Sort.Direction getDirection(String sortInstruction) {
        Sort.Direction direction = DEFAULT_DIRECTION;
        String[] split = sortInstruction.split(":");
        if (split.length > 1) {
            direction = HateosDirection.valueOf(split[1]).getDirection();
        }
        return direction;
    }

    enum HateosDirection{
        asc(Sort.Direction.Ascending),desc(Sort.Direction.Descending);

        Sort.Direction direction;

        HateosDirection(Sort.Direction direction) {
            this.direction = direction;
        }

        public Sort.Direction getDirection() {
            return direction;
        }
    }
}
