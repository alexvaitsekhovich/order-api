package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.dto.OrderDtoPagedList;
import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.entity.PaymentInformation;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;

import static com.alexvait.orderapi.testobjects.TestData.testOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("testing")
@DisplayName("Integration test for Order controller")
class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    private OrderService orderService;

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = OrderMapper.INSTANCE;
    }

    @Test
    @DisplayName("Test getAllOrdersByCustomerId")
    void testGetAllOrdersByCustomerId() {
        // arrange
        Order order2 = new Order("", 1L, 100L, new PaymentInformation(0, 0, 0));

        List<OrderDto> ordersDto = List.of(orderMapper.orderToOrderDto(testOrder), orderMapper.orderToOrderDto(order2));
        OrderDtoPagedList orderDtoPagedList = new OrderDtoPagedList(ordersDto);

        when(orderService.getOrdersByCustomerId(anyLong(), any(PageRequest.class))).thenReturn(orderDtoPagedList);

        // act
        ResponseEntity<OrderDtoPagedList> ordersPagedList = orderController.getAllOrdersByCustomerId(1, 0, 2);

        //assert
        verify(orderService, times(1)).getOrdersByCustomerId(anyLong(), any());

        OrderDtoPagedList returnedOrders = ordersPagedList.getBody();
        assertEquals(2, returnedOrders.getTotalElements());

        assertThat(
                returnedOrders.stream().collect(Collectors.toList()),
                containsInAnyOrder(ordersDto.toArray())
        );
    }

    @Test
    @DisplayName("Test getOrderById")
    void testGetOrderById() {
        // arrange
        when(orderService.findById(anyLong())).thenReturn(testOrder);

        // act
        ResponseEntity<OrderDto> responseEntity = orderController.getOrderById(1);

        //assert
        assertEquals(orderMapper.orderToOrderDto(testOrder), responseEntity.getBody());
        verify(orderService, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Test saveOrder")
    void testSaveOrder() throws Exception {
        // arrange
        when(orderService.save(any(Order.class))).thenReturn(testOrder);

        // act
        ResponseEntity<OrderDto> responseEntity = orderController.saveOrder(orderMapper.orderToOrderDto(testOrder));

        //assert
        assertEquals(orderMapper.orderToOrderDto(testOrder), responseEntity.getBody());
        verify(orderService, times(1)).save(any());
    }

    @Test
    @DisplayName("Test updateStatus")
    void testUpdateStatus() throws Exception {
        // arrange
        when(orderService.changeStatus(anyLong(), anyString())).thenReturn(testOrder);

        // act
        ResponseEntity<OrderDto> responseEntity = orderController.updateStatus(1L, "x");

        //assert
        assertEquals(orderMapper.orderToOrderDto(testOrder), responseEntity.getBody());
        verify(orderService, times(1)).changeStatus(anyLong(), anyString());
    }
}
