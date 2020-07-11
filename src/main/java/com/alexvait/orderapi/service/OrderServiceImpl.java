package com.alexvait.orderapi.service;

import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.entity.OrderStatus;
import com.alexvait.orderapi.exception.NotFoundException;
import com.alexvait.orderapi.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getOrders(int page, int size, String sortDirection, String sort) {
        PageRequest pageReq = PageRequest.of(page, size,
                Sort.Direction.fromString(sortDirection), sort);

        Page<Order> orders = orderRepository.findAll(pageReq);
        return orders.getContent();
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
