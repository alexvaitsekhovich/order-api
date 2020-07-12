package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "2") int size,
                                                       @RequestParam(defaultValue = "asc") String sortDirection,
                                                       @RequestParam(defaultValue = "id") String sortBy
    ) {
        List<Order> orders = orderService.getOrders(page, size, sortDirection, sortBy);
        return ResponseEntity.ok(orders.stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<OrderDto> saveOrder(@Valid @RequestBody OrderDto receivedOrderDto) {
        log.info("Creating new order from dto : " + receivedOrderDto);
        Order order = orderService.save(orderMapper.orderDtoToOrder(receivedOrderDto));
        return new ResponseEntity<>(orderMapper.orderToOrderDto(order), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable long orderId) {
        log.info("Getting order by id #" + orderId);
        Order order = orderService.findById(orderId);
        return ResponseEntity.ok(orderMapper.orderToOrderDto(order));
    }

    @PatchMapping("/{orderId}/actions/{action}")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable long orderId, @PathVariable String action) {
        Order order = orderService.changeStatus(orderId, action);
        return ResponseEntity.ok(orderMapper.orderToOrderDto(order));
    }
}
