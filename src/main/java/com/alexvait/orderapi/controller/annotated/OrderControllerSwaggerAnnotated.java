package com.alexvait.orderapi.controller.annotated;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.dto.OrderDtoPagedList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface OrderControllerSwaggerAnnotated {

    @Operation(
            summary = "Get all orders",
            tags = "Ordering",
            description = "Use this endpoint to get a list of all orders for the specified customer id, with pagination"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of orders was created", content = {@Content(mediaType = "application/json")})}
    )
    ResponseEntity<OrderDtoPagedList> getAllOrdersByCustomerId(
            @Parameter(description = "Customer id") long customerId,
            @Parameter(description = "Page number") Integer page,
            @Parameter(description = "Page size") Integer size);


    @Operation(
            summary = "Get order by id",
            tags = "Ordering",
            description = "Use this endpoint to get an order"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order was found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Order was not found", content = {@Content(mediaType = "application/json")})}
    )
    ResponseEntity<OrderDto> getOrderById(@Parameter(description = "Order id") long orderId);


    @Operation(
            summary = "Create an order",
            tags = "Ordering",
            description = "Use this endpoint to create an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order was created", content = {@Content(mediaType = "application/json")})}
    )
    ResponseEntity<OrderDto> saveOrder(
            @Parameter(description = "Data of the order to be created") OrderDto receivedOrderDto) throws URISyntaxException;


    @Operation(
            summary = "Update status of an order",
            tags = "Ordering",
            description = "Use this endpoint to update the order status"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status was updated", content = {@Content(mediaType = "application/json")})}
    )
    ResponseEntity<OrderDto> updateStatus(
            @Parameter(description = "Order id") long orderId,
            @Parameter(description = "Order state as string, allowed values: \n" +
                    "* 0 - created (default on order creation)\n" +
                    "* 1 - approved\n" +
                    "* 2 - delivered\n" +
                    "* 3 - cancelled\n" +
                    "* 4 - fake") String action);
}