package com.alexvait.orderapi.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;

@DisplayName("Test ApiError")
class ApiErrorTest {

    private ApiError apiError;

    @BeforeEach
    void setUp() {
        apiError = new ApiError(HttpStatus.OK);
    }


    @Test
    @DisplayName("Test setting errors")
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