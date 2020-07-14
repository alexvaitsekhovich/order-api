package com.alexvait.orderapi.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public final class ControllerPaginationHelper {
    public static final String DEFAULT_PAGE = "0";
    public static final int DEFAULT_PAGE_INT = Integer.parseInt(DEFAULT_PAGE);

    public static final String DEFAULT_SIZE = "10";
    public static final int DEFAULT_SIZE_INT = Integer.parseInt(DEFAULT_SIZE);

    public static final String DEFAULT_DIRECTION = "asc";

    public static final String DEFAULT_SORT = "id";

    public static final PageRequest DEFAULT_PAGE_REQUEST = PageRequest.of(
            DEFAULT_PAGE_INT, DEFAULT_SIZE_INT,
            Sort.Direction.fromString(DEFAULT_DIRECTION), DEFAULT_SORT);
}
