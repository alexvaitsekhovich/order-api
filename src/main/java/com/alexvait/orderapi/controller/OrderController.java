package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.hateoas.OrderDtoHateoasAssembler;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    private final OrderDtoHateoasAssembler hateoasAssembler;

    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";
    public static final String DEFAULT_DIRECTION = "asc";
    public static final String DEFAULT_SORT = "id";

    public static final int DEFAULT_PAGE_INT = Integer.parseInt(DEFAULT_PAGE);
    public static final int DEFAULT_SIZE_INT = Integer.parseInt(DEFAULT_SIZE);

    public OrderController(OrderService orderService,
                           OrderMapper orderMapper,
                           OrderDtoHateoasAssembler hateoasAssembler) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.hateoasAssembler = hateoasAssembler;
    }

    public CollectionModel<EntityModel<OrderDto>> getAllOrders() {
        return getAllOrders(DEFAULT_PAGE_INT, DEFAULT_SIZE_INT, DEFAULT_DIRECTION, DEFAULT_SORT);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get the list of orders with pagination")
    public CollectionModel<EntityModel<OrderDto>> getAllOrders(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                               @RequestParam(defaultValue = DEFAULT_SIZE) int size,
                                                               @RequestParam(defaultValue = DEFAULT_DIRECTION) String sortDirection,
                                                               @RequestParam(defaultValue = DEFAULT_SORT) String sortBy
    ) {
        List<Order> orders = orderService.getOrders(page, size, sortDirection, sortBy);

        List<OrderDto> orderDtos = orders.stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());

        return hateoasAssembler.toCollectionModelWithPagination(orderDtos, page, size, sortDirection, sortBy);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get the order by id")
    public EntityModel<OrderDto> getOrderById(@PathVariable long orderId) {
        log.info("Getting order by id #" + orderId);
        return hateoasAssembler.toModel(
                orderMapper.orderToOrderDto(
                        orderService.findById(orderId)
                )
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create an order")
    public EntityModel<OrderDto> saveOrder(@Valid @RequestBody OrderDto receivedOrderDto) {
        log.info("Creating new order from dto : " + receivedOrderDto);
        return hateoasAssembler.toModel(
                orderMapper.orderToOrderDto(
                        orderService.save(orderMapper.orderDtoToOrder(receivedOrderDto))
                )
        );
    }

    @PatchMapping("/{orderId}/actions/{action}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update order status", notes = "Valid actions: 'create', 'approve', 'deliver', 'cancel', 'fake'")
    public EntityModel<OrderDto> updateStatus(@PathVariable long orderId, @PathVariable String action) {
        log.info(String.format("Updating status of the order #%d with action '%s'", orderId, action));
        return hateoasAssembler.toModel(
                orderMapper.orderToOrderDto(
                        orderService.changeStatus(orderId, action)
                )
        );
    }
}
