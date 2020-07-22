package com.alexvait.orderapi.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;

class ApiErrorTest {

    private ApiError apiError;

    @BeforeEach
    void setUp() {
        apiError = new ApiError(HttpStatus.OK);
    }


    @Test
    void setErrors() {
        // assign
        apiError.setErrors(Arrays.asList("Error1", "Error2"));

        // act
        Map<String, Object> errorBody = apiError.getErrorBody();

        // assert
        assertThat(errorBody.get("errors"), instanceOf(List.class));
        assertThat((List<?>)errorBody.get("errors"), hasSize(2));
    }
}