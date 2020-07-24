package com.alexvait.orderapi.service;

import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.entity.OrderStatus;
import com.alexvait.orderapi.exception.NotFoundException;
import com.alexvait.orderapi.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getOrders() {
        return null;
    }

    @Override
    public Page<Order> getOrders(PageRequest pageRequest) {
        Page<Order> orderPage = orderRepository.findAll(pageRequest);

        return new PageImpl<>(
                orderPage.getContent(),
                orderPage.getPageable(),
                orderPage.getTotalElements()
        );
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id %d not found", id)));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order changeStatus(long orderId, String action) {
        Order order = findById(orderId);
        order.setStatusId(OrderStatus.getStatusByAction(action));
        return orderRepository.save(order);
    }
}
