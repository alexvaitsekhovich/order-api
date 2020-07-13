package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(OrderController.BASE_URL)
@Slf4j
@Api(tags = {"Order controller"})
public class OrderController {

    public static final String BASE_URL = "/api/v1/orders";

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get the list of orders with pagination")
    public List<OrderDto> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "2") int size,
                                       @RequestParam(defaultValue = "asc") String sortDirection,
                                       @RequestParam(defaultValue = "id") String sortBy
    ) {
        List<Order> orders = orderService.getOrders(page, size, sortDirection, sortBy);
        return orders.stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get the order by id")
    public OrderDto getOrderById(@PathVariable long orderId) {
        log.info("Getting order by id #" + orderId);
        Order order = orderService.findById(orderId);
        return orderMapper.orderToOrderDto(order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an order")
    public OrderDto saveOrder(@Valid @RequestBody OrderDto receivedOrderDto) {
        log.info("Creating new order from dto : " + receivedOrderDto);
        Order order = orderService.save(orderMapper.orderDtoToOrder(receivedOrderDto));
        return orderMapper.orderToOrderDto(order);
    }

    @PatchMapping("/{orderId}/actions/{action}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update order status", notes = "Valid actions: 'create', 'approve', 'deliver', 'cancel', 'fake'")
    public OrderDto updateStatus(@PathVariable long orderId, @PathVariable String action) {
        Order order = orderService.changeStatus(orderId, action);
        return orderMapper.orderToOrderDto(order);
    }
}
