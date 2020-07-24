package com.alexvait.orderapi.service;

import com.alexvait.orderapi.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<Order> getOrders();

    Page<Order> getOrders(PageRequest pageRequest);

    Order findById(Long id);

    Order save(Order order);

    Order changeStatus(long orderId, String action);
}
