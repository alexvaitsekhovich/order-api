package com.alexvait.orderapi.entity;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.alexvait.orderapi.testobjects.TestData.testOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Order part entity")
class OrderPartTest {

    @Test
    @DisplayName("Verify that the order is not included into toString()")
    void testToString() {

        // arrange
        Random random = new Random(31);

        Long ITEM_ID = (long) random.nextInt(1000);
        String ORDER_NUMBER = RandomStringUtils.randomAlphanumeric(100);
        String ITEM_NAME = RandomStringUtils.randomAlphanumeric(10);
        int AMOUNT = random.nextInt(1000);
        int PRICE = random.nextInt(10000);

        Order o = new Order(ORDER_NUMBER, new PaymentInformation(1, 1, 1));
        o.setAddress(testOrder.getAddress());

        OrderPart orderPart = new OrderPart(ITEM_ID, ITEM_NAME, AMOUNT, PRICE);
        o.addPart(orderPart);

        // act, assert
        // verify that the order is not included into toString method
        String expected = String.format("OrderPart(id=null, itemId=%d, itemName=%s, count=%d, price=%d)",
                ITEM_ID, ITEM_NAME, AMOUNT, PRICE);
        assertEquals(expected, orderPart.toString());
    }
}