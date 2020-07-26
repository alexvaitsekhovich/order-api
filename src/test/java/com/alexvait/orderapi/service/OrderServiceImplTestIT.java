package com.alexvait.orderapi.service;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.dto.OrderDtoPagedList;
import com.alexvait.orderapi.mapper.OrderMapperImpl;
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

import static com.alexvait.orderapi.testobjects.TestData.testPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("testing")
@DisplayName("Integration test for Order service implementation")
class OrderServiceImplTestIT {

    @Autowired
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository, new OrderMapperImpl());
    }

    @Test
    @DisplayName("Test get multiple orders")
    void getOrders() {
        // arrange

        // act
        OrderDtoPagedList ordersDtoPagedList = orderService.getOrders(testPageable);

        // assert
        List<OrderDto> orderDtoList = ordersDtoPagedList.getContent();
        assertEquals(2, orderDtoList.size());
        assertEquals(2, orderDtoList.size());
        OrderDto order = orderDtoList.get(0);

        assertEquals(1, order.getId());
        assertEquals("AX1", order.getNumber());
        assertEquals(2, order.getOrderParts().size());
    }
}