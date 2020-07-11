package com.alexvait.orderapi.entity;

import com.alexvait.orderapi.exception.IllegalOrderStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Test OrderStatus")
class OrderStatusTest {

    @Test
    @DisplayName("Test method getStatusByAction() for every existing status")
    public void getStatusByAction() {

        // arrange
        // act

        // assert
        Stream.of(OrderStatus.values())
                .forEach(orderStatus -> assertEquals(
                        orderStatus.getId(),
                        OrderStatus.getStatusByAction(orderStatus.getAction())
                ));
    }

    @Test
    @DisplayName("Test method getStatusByAction() for not existing status")
    public void getStatusByActionNotExisting() {

        // arrange
        // act

        // assert
        assertThrows(IllegalOrderStatusException.class,
                () -> OrderStatus.getStatusByAction("NOT EXISTING"));
    }

}