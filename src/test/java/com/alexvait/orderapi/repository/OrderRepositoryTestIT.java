package com.alexvait.orderapi.repository;

import com.alexvait.orderapi.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("testing")
@DisplayName("Integration test for order repository")
class OrderRepositoryTestIT {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Integration test for findById()")
    public void testFindById() {

        // arrange

        // act
        Order order = orderRepository.findById(1L).orElse(null);

        // assert
        assertEquals(1, order.getId());
        assertEquals("AX1", order.getNumber());
        assertEquals(0, order.getStatusId());
        assertEquals(1, order.getPaymentInformation().getPaymentId());
        assertEquals(200, order.getPaymentInformation().getAmount());
        assertEquals(0, order.getPaymentInformation().getDiscountAmount());
        assertEquals(2, order.getOrderParts().size());
        assertEquals(1, order.getAddress().getId());
    }
}