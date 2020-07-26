package com.alexvait.orderapi.mapper;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static com.alexvait.orderapi.testobjects.TestData.testOrder;
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

    @Test
    void timestampToOffsetDateTime() {
        Timestamp ts = Timestamp.valueOf("2020-03-12 13:11:22.0");
        OffsetDateTime odt = orderMapper.timestampToOffsetDateTime(ts);

        assertEquals(2020, odt.getYear());
        assertEquals(3, odt.getMonthValue());
        assertEquals(12, odt.getDayOfMonth());
        assertEquals(13, odt.getHour());
        assertEquals(11, odt.getMinute());
        assertEquals(22, odt.getSecond());
    }

    @Test
    void offsetDateTimeToTimestamp() {
        OffsetDateTime odt = OffsetDateTime.parse("2020-03-12T13:11:22+00:00");
        Timestamp ts = orderMapper.offsetDateTimeToTimestamp(odt);

        assertEquals(2020, ts.toLocalDateTime().getYear());
        assertEquals(3, ts.toLocalDateTime().getMonthValue());
        assertEquals(12, ts.toLocalDateTime().getDayOfMonth());
        assertEquals(13, ts.toLocalDateTime().getHour());
        assertEquals(11, ts.toLocalDateTime().getMinute());
        assertEquals(22, ts.toLocalDateTime().getSecond());

    }
}