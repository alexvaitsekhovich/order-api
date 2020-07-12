package com.alexvait.orderapi.service;

import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("it")
@DisplayName("Integration test for Order service implementation")
class OrderServiceImplTestIT {

    @Autowired
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    @DisplayName("Test get multiple orders")
    void getOrders() {
        // arrange

        // act
        List<Order> orders = orderService.getOrders(0, 10, "asc", "id");

        // assert
        assertEquals(2, orders.size());

        Order order = orders.get(0);

        assertEquals(1, order.getId());
        assertEquals("AX", order.getNumber());
        assertEquals(2, order.getOrderParts().size());
    }
}