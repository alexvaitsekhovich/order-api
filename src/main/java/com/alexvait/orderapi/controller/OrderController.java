package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.dto.OrderDtoPagedList;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(OrderController.BASE_URL)
@Slf4j
@AllArgsConstructor
public class OrderController {

    public static final String BASE_URL = "/api/v1/orders";
    public static final String BASE_ORDER_URL = BASE_URL + "/order";
    public static final String BASE_CUSTOMER_URL = BASE_URL + "/customer";

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<OrderDtoPagedList> getAllOrdersByCustomerId(
            @PathVariable("customerId") long customerId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {

        log.debug(String.format("Getting orders for customer id: #%s, page: %s, size: %s",
                customerId, page, size));

        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId, PageRequest.of(page, size)));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("orderId") long orderId) {

        log.debug(String.format("Getting order by id #%s", orderId));
        return ResponseEntity.ok(
                orderMapper.orderToOrderDto(
                        orderService.findById(orderId)
                )
        );
    }

    @PostMapping
    public ResponseEntity<OrderDto> saveOrder(@Valid @RequestBody OrderDto receivedOrderDto) throws URISyntaxException {

        log.debug(String.format("Creating new order from dto : %s", receivedOrderDto));
        OrderDto orderDto = orderMapper.orderToOrderDto(
                orderService.save(
                        orderMapper.orderDtoToOrder(receivedOrderDto)
                )
        );

        return ResponseEntity.created(
                new URI(OrderController.BASE_ORDER_URL + "/" + orderDto.getId())
        ).body(orderDto);
    }

    @PatchMapping("/{orderId}/actions/{action}")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable("orderId") long orderId, @PathVariable String action) {

        log.debug(String.format("Updating status of the order #%d with action '%s'", orderId, action));
        return ResponseEntity.ok(
                orderMapper.orderToOrderDto(
                        orderService.changeStatus(orderId, action)
                )
        );
    }
}
