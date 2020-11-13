package com.alexvait.orderapi.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Test ApiError")
class ApiErrorTest {

    private ApiError apiError;

    @BeforeEach
    void setUp() {
        apiError = new ApiError(HttpStatus.OK);
    }


    @Test
    @DisplayName("Test setting errors")
    void testSetErrors() {
        // assign
        apiError.setErrors(Arrays.asList("Error1", "Error2"));

        // act
        Map<String, Object> errorBody = apiError.getErrorBody();

        // assert
        assertThat(errorBody.get("errors"), instanceOf(List.class));
        assertThat((List<?>)errorBody.get("errors"), hasSize(2));
    }

    @Test
    @DisplayName("Test setting no errors")
    void testSetEmptyErrors() {
        // assign
        apiError.setErrors(Collections.emptyList());

        // act

        // assert
        assertThat(apiError.getErrorBody().get("errors"), nullValue());
    }

    @Test
    @DisplayName("Test setting path")
    void testSetPath() {
        // assign
        String path = "somepath";
        apiError.setPath(path);

        // act
        String savedPath = (String) apiError.getErrorBody().get("path");

        // assert
        assertThat(savedPath, equalTo(path));
    }

}