package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDto;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.alexvait.orderapi.testobjects.TestOrder.testOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("testing")
@DisplayName("Integration test for Order controller")
class OrderControllerTestIT {

    @Autowired
    private OrderRepository orderRepository;

    private OrderController orderController;
    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapperImpl();
        orderController = new OrderController(new OrderServiceImpl(orderRepository), orderMapper);
    }

    @Test
    @DisplayName("Test getting all orders")
    void testGetAllOrders() {

        // arrange

        // act
        List<OrderDto> orders = orderController.getAllOrders().getBody();

        // assert

        OrderDto order = orders.get(0);

        assertEquals(2, orders.size());
        assertEquals(1, order.getId());
        assertEquals("AX", order.getNumber());
        assertEquals(2, order.getOrderParts().size());
    }

    @Test
    @DisplayName("Test saving an order with invalid request body")
    public void testSaveValidationException() throws Exception {

        // arrange
        OrderDto orderDto = orderMapper.orderToOrderDto(testOrder);
        orderDto.setStatusId(null);

        // act, assert
        assertThrows(ConstraintViolationException.class, () -> orderController.saveOrder(orderDto));
    }
}
