package com.alexvait.orderapi.service;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.dto.OrderDtoPagedList;
import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.entity.OrderStatus;
import com.alexvait.orderapi.entity.PaymentInformation;
import com.alexvait.orderapi.exception.IllegalOrderStatusException;
import com.alexvait.orderapi.exception.NotFoundException;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.alexvait.orderapi.testobjects.TestData.testOrder;
import static com.alexvait.orderapi.testobjects.TestData.testPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Order service implementation")
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;
    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = OrderMapper.INSTANCE;
        orderService = new OrderServiceImpl(orderRepository, orderMapper);
    }

    @Test
    @DisplayName("Test get multiple orders")
    void getOrders() {

        // arrange
        List<Order> orders = Collections.singletonList(testOrder);
        List<OrderDto> expectedOrdersDto = Collections.singletonList(orderMapper.orderToOrderDto(testOrder));

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(orders));

        // act
        OrderDtoPagedList returnedOrdersPagedList = orderService.getOrders(testPageable);

        // assert
        assertEquals(expectedOrdersDto, returnedOrdersPagedList.getContent());
    }

    @Test
    @DisplayName("Test order found by id")
    void findByIdFound() {

        // arrange
        long id = testOrder.getId();
        when(orderRepository.findById(id)).thenReturn(Optional.of(testOrder));

        // act
        Order returnedOrder = orderService.findById(id);

        // assert
        assertEquals(testOrder, returnedOrder);
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test no order found by id")
    void testFindByIdNotFound() {

        // arrange
        long id = testOrder.getId();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        // act, assert
        assertThrows(NotFoundException.class, () -> orderService.findById(id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test saving an order")
    void save() {

        // arrange

        // act
        orderService.save(testOrder);

        // assert
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Test order status change")
    void changeStatusSuccess() {

        // arrange
        Order order = new Order("", new PaymentInformation(0, 0, 0));
        OrderStatus newStatus = OrderStatus.CANCELLED;
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        // act
        orderService.changeStatus(1, newStatus.getAction());

        // assert
        assertEquals(newStatus.getId(), order.getStatusId());
        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Test order status change for illegal status action")
    void changeStatusFailure() {

        // arrange
        String illegalStatus = "NOT EXISTING";
        Order order = new Order("", new PaymentInformation(0, 0, 0));
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        // act, assert
        assertThrows(IllegalOrderStatusException.class,
                () -> orderService.changeStatus(1, illegalStatus));

        assertEquals(0, order.getStatusId());
        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(0)).save(any(Order.class));
    }
}