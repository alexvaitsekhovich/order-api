package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.hateoas.OrderDtoHateoasAssembler;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.mapper.OrderMapperImpl;
import com.alexvait.orderapi.repository.OrderRepository;
import com.alexvait.orderapi.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("it")
@DisplayName("Integration test for Order controller")
class OrderControllerTestIT {

    @Autowired
    private OrderRepository orderRepository;

    private OrderController orderController;
    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapperImpl();
        orderController = new OrderController(
                new OrderServiceImpl(orderRepository),
                orderMapper,
                new OrderDtoHateoasAssembler());
    }

    @Test
    @DisplayName("Test getting all orders")
    void testGetAllOrders() {

        // arrange

        // act
        CollectionModel<EntityModel<OrderDto>> resultOrders = orderController.getAllOrders();

        // assert
        List<OrderDto> orders = resultOrders.getContent()
                .stream()
                .map(EntityModel::getContent)
                .collect(Collectors.toList());

        OrderDto order = orders.get(0);

        assertEquals(2, orders.size());
        assertEquals(1, order.getId());
        assertEquals("AX", order.getNumber());
        assertEquals(2, order.getOrderParts().size());

    }
}
