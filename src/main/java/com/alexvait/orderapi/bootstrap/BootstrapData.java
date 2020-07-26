package com.alexvait.orderapi.bootstrap;

import com.alexvait.orderapi.entity.Address;
import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.entity.OrderPart;
import com.alexvait.orderapi.entity.PaymentInformation;
import com.alexvait.orderapi.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Profile({"dev"})
public class BootstrapData implements CommandLineRunner {
    private final OrderService orderService;

    public BootstrapData(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Order o = new Order("1234", new PaymentInformation(3, 9212, 0));

        o.addPart(new OrderPart(3L, "Bread", 1, 99));
        o.addPart(new OrderPart(4L, "Tomato", 12, 456));

        o.setAddress(new Address("Berlin", "10115", "Chausseestr.", "11"));

        orderService.save(o);
    }
}
