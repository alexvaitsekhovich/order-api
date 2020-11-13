package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.dto.OrderDtoPagedList;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.alexvait.orderapi.testobjects.TestData.testOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@EnableSpringDataWebSupport
@ActiveProfiles("testing")
@DisplayName("Integration test for Order controller")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    @Test
    @DisplayName("Test order get with defined pagination")
    void testGetOrdersWithDefinedPagingParameters() throws Exception {

        // arrange
        when(orderService.getOrdersByCustomerId(anyLong(), any())).thenReturn(
                new OrderDtoPagedList(
                        Collections.singletonList(
                                OrderMapper.INSTANCE.orderToOrderDto(testOrder)
                        )
                )
        );

        // act
        mockMvc.perform(get(OrderController.BASE_CUSTOMER_URL + "/1")
                .param("page", "5")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // assert
        ArgumentCaptor<PageRequest> pageableCaptor = ArgumentCaptor.forClass(PageRequest.class);

        verify(orderService).getOrdersByCustomerId(anyLong(), pageableCaptor.capture());
        Pageable pageable = pageableCaptor.getValue();

        assertEquals(5, pageable.getPageNumber());
        assertEquals(10, pageable.getPageSize());
    }

    @Test
    @DisplayName("Test order get with default pagination")
    void testGetOrdersWithDefaultPagingParameters() throws Exception {

        // arrange
        when(orderService.getOrdersByCustomerId(anyLong(), any())).thenReturn(
                new OrderDtoPagedList(
                        Collections.singletonList(
                                OrderMapper.INSTANCE.orderToOrderDto(testOrder)
                        )
                )
        );

        // act
        mockMvc.perform(get(OrderController.BASE_CUSTOMER_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // assert
        ArgumentCaptor<PageRequest> pageableCaptor = ArgumentCaptor.forClass(PageRequest.class);

        verify(orderService).getOrdersByCustomerId(anyLong(), pageableCaptor.capture());
        Pageable pageable = pageableCaptor.getValue();

        assertEquals(0, pageable.getPageNumber());
        assertEquals(5, pageable.getPageSize());

    }
}
