package com.alexvait.orderapi.service;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<Order> getOrders(int page, int size, String sortDir, String sort);

    Order findById(Long id);

    Order save(Order order);

    Order changeStatus(long orderId, String action);
}
