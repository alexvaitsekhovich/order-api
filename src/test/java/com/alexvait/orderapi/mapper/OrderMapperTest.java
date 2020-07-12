package com.alexvait.orderapi.mapper;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.alexvait.orderapi.testobjects.TestOrder.testOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Order-DTO mapper")
class OrderMapperTest {

    private static final String CITY = "Berlin";
    private static final String ZIP = "10115";
    private static final String STREET = "Chausseestr.";
    private static final String NR = "1";

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
    }

    @Test
    public void testOrderToOrderDtoWithNull() {

        // arrange

        // act
        OrderDto orderDto = orderMapper.orderToOrderDto(new Order());

        // assert
        assertEquals(null, orderDto.getId());
        assertEquals(null, orderDto.getNumber());
        assertEquals(null, orderDto.getPaymentId());
        assertEquals(null, orderDto.getAmount());
        assertEquals(null, orderDto.getDiscountAmount());
        assertEquals(null, orderDto.getAddress());
        assertEquals(0, orderDto.getOrderParts().size());
    }

}