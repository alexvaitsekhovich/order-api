package com.alexvait.orderapi.mapper;

import com.alexvait.orderapi.dto.OrderDto;
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
}