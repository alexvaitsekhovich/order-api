package com.alexvait.orderapi.mapper;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.alexvait.orderapi.testobjects.TestOrder.testOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Test Order-DTO mapper")
class OrderMapperTest {

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    @Test
    public void testOrderToOrderDto() {

        // arrange

        // act
        OrderDto orderDto = orderMapper.orderToOrderDto(testOrder);

        // assert
        assertEquals(testOrder.getId(), orderDto.getId());
        assertEquals(testOrder.getNumber(), orderDto.getNumber());

        assertEquals(testOrder.getPaymentInformation().getPaymentId(), orderDto.getPaymentId());
        assertEquals(testOrder.getPaymentInformation().getAmount(), orderDto.getAmount());
        assertEquals(testOrder.getPaymentInformation().getDiscountAmount(), orderDto.getDiscountAmount());

        assertEquals(testOrder.getAddress().getCity(), orderDto.getAddress().getCity());
        assertEquals(testOrder.getAddress().getZip(), orderDto.getAddress().getZip());
        assertEquals(testOrder.getAddress().getStreet(), orderDto.getAddress().getStreet());
        assertEquals(testOrder.getAddress().getNr(), orderDto.getAddress().getNr());

        assertEquals(2, orderDto.getOrderParts().size());
    }

    @Test
    public void testOrderToOrderDtoWithNull() {

        // arrange

        // act
        OrderDto orderDto = orderMapper.orderToOrderDto(new Order());

        // assert
        assertNull(orderDto.getId());
        assertNull(orderDto.getNumber());
        assertNull(orderDto.getPaymentId());
        assertNull(orderDto.getAmount());
        assertNull(orderDto.getDiscountAmount());
        assertNull(orderDto.getAddress());
        assertEquals(0, orderDto.getOrderParts().size());
    }

}