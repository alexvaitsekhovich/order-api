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
import java.util.stream.IntStream;

@Component
@Profile({"dev", "cloud"})
public class BootstrapData implements CommandLineRunner {
    private final OrderService orderService;

    public BootstrapData(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Order o = new Order("AX1", new PaymentInformation(3, 9212, 0));

        o.addPart(new OrderPart(3L, "Bread", 1, 99));
        o.addPart(new OrderPart(4L, "Tomato", 12, 456));

        o.setAddress(new Address("Berlin", "10115", "Chausseestr.", "11"));

        orderService.save(o);

        IntStream.range(1, 5)
                .forEach(i -> {
                            Order order = new Order("AX" + i, new PaymentInformation(1, i * 100, 0));
                            order.setAddress(new Address("Nowhere", "10000", "Empty Street.", "-10"));
                            orderService.save(order);
                        }
                );

    }
}
