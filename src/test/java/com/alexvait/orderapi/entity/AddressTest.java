package com.alexvait.orderapi.entity;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Address entity")
class AddressTest {

    @Test
    @DisplayName("Verify that the order is not included into toString()")
    void testToString() {

        // arrange
        String orderNumber = RandomStringUtils.randomAlphabetic(10);
        String city = RandomStringUtils.randomAlphabetic(100);
        String zip = RandomStringUtils.randomAlphabetic(10);
        String street = RandomStringUtils.randomAlphabetic(100);
        String nr = RandomStringUtils.randomAlphabetic(10);

        Random r = new Random(32);
        Long customerId = r.nextLong();
        Long retailerId = r.nextLong();

        Order o = new Order(orderNumber, customerId, retailerId, new PaymentInformation(1, 1, 1));

        Address address = new Address(city, zip, street, nr);
        o.setAddress(address);

        // act, assert
        // verify that the order is not included into toString method
        String expected = String.format("Address(id=null, city=%s, zip=%s, street=%s, nr=%s)",
                city, zip, street, nr);
        assertEquals(expected, address.toString());
    }
}