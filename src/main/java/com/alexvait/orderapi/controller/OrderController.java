package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(OrderController.BASE_URL)
@Slf4j
@AllArgsConstructor
public class OrderController {

    public static final String BASE_URL = "/api/v1/orders";

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orderDtos = orderService.getOrders()
                .stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("orderId") long orderId) {
        log.info("Getting order by id #" + orderId);
        return ResponseEntity.ok(orderMapper.orderToOrderDto(orderService.findById(orderId)));
    }

    @PostMapping
    public ResponseEntity<OrderDto> saveOrder(@Valid @RequestBody OrderDto receivedOrderDto) throws URISyntaxException {
        log.info("Creating new order from dto : " + receivedOrderDto);

        OrderDto orderDto = orderMapper.orderToOrderDto(
                orderService.save(orderMapper.orderDtoToOrder(receivedOrderDto))
        );

        return ResponseEntity.created(new URI(OrderController.BASE_URL + "/" + orderDto.getId())).body(orderDto);
    }

    @PatchMapping("/{orderId}/actions/{action}")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable("orderId") long orderId, @PathVariable String action) {
        log.info(String.format("Updating status of the order #%d with action '%s'", orderId, action));
        return ResponseEntity.ok(orderMapper.orderToOrderDto(orderService.changeStatus(orderId, action)));
    }
}
