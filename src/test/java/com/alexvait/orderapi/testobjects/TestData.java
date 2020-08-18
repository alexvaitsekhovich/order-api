package com.alexvait.orderapi.testobjects;

import com.alexvait.orderapi.entity.Address;
import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.entity.OrderPart;
import com.alexvait.orderapi.entity.PaymentInformation;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;

@Getter
public class TestData implements Cloneable {
    public static final Order testOrder;
    public static final PageRequest testPageable;

    static {
        testOrder = new Order("ABX1", 1L, 100L, new PaymentInformation(1, 1200, 0));
        testOrder.setId(4L);
        testOrder.addPart(new OrderPart(12L, "Pizza", 1, 1000));
        testOrder.addPart(new OrderPart(13L, "Fanta", 1, 200));
        testOrder.setAddress(new Address("Berlin", "10115", "Chausseestr.", "11"));

        testPageable = PageRequest.of(0, 2);
    }
}
