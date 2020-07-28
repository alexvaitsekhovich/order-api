package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final PagedResourcesAssembler<OrderDto> assembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<OrderDto>>> getAllOrders(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {

        log.debug(String.format("Getting orders, page: %s, size: %s, sort: %s",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));

        return ResponseEntity.ok(
                assembler.toModel(
                        orderService.getOrders(pageable)
                )
        );
    }

    @GetMapping("/{orderId}")
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
                new URI(OrderController.BASE_URL + "/" + orderDto.getId())
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
