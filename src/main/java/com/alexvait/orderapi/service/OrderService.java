package com.alexvait.orderapi.service;

import com.alexvait.orderapi.dto.OrderDtoPagedList;
import com.alexvait.orderapi.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderDtoPagedList getOrders(Pageable pageable);

    Order findById(Long id);

    Order save(Order order);

    Order changeStatus(long orderId, String action);
}
