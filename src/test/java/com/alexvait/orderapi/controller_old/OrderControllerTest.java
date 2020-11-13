package com.alexvait.orderapi.controller_old;

import com.alexvait.orderapi.controller.ControllerExceptionHandler;
import com.alexvait.orderapi.controller.OrderController;
import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.dto.OrderDtoPagedList;
import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.exception.IllegalOrderStatusException;
import com.alexvait.orderapi.exception.NotFoundException;
import com.alexvait.orderapi.mapper.OrderMapper;
import com.alexvait.orderapi.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.alexvait.orderapi.testobjects.TestData.testOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("testing")
@DisplayName("Test Order controller")
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    private OrderMapper orderDtoMapper;
    private ObjectMapper jsonMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        orderDtoMapper = OrderMapper.INSTANCE;
        jsonMapper = new ObjectMapper();

        OrderController orderController = new OrderController(orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Test successfully find order by id")
    public void testGetOrder() throws Exception {

        // arrange
        when(orderService.findById(anyLong())).thenReturn(testOrder);

        // act, assert
        MvcResult result = mockMvc.perform(get(OrderController.BASE_ORDER_URL + "/" + testOrder.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testOrder.getId()))
                .andExpect(jsonPath("$.number").value(testOrder.getNumber()))
                .andExpect(jsonPath("$.customerId").value(testOrder.getCustomerId()))
                .andExpect(jsonPath("$.retailerId").value(testOrder.getRetailerId()))
                .andExpect(jsonPath("$.statusId").value(testOrder.getStatusId()))
                .andExpect(jsonPath("$.paymentId").value(
                        testOrder.getPaymentInformation().getPaymentId()))
                .andExpect(jsonPath("$.amount").value(
                        testOrder.getPaymentInformation().getAmount()))
                .andExpect(jsonPath("$.discountAmount").value(
                        testOrder.getPaymentInformation().getDiscountAmount()))
                .andExpect(jsonPath("$.address.city").value(testOrder.getAddress().getCity()))
                .andExpect(jsonPath("$.address.zip").value(testOrder.getAddress().getZip()))
                .andExpect(jsonPath("$.address.street").value(testOrder.getAddress().getStreet()))
                .andExpect(jsonPath("$.address.nr").value(testOrder.getAddress().getNr()))
                .andExpect(jsonPath("$.orderParts", hasSize(2)))
                .andReturn();

        EntityModel<OrderDto> returnedOrderDtoModel = jsonMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<EntityModel<OrderDto>>() {
                }
        );
        OrderDto createdOrderDto = returnedOrderDtoModel.getContent();

        assertEquals(testOrder, orderDtoMapper.orderDtoToOrder(createdOrderDto));
        verify(orderService, times(1)).findById(testOrder.getId());
    }

    @Test
    @DisplayName("Test order not found by id")
    public void testGetOrderNotFound() throws Exception {

        // arrange
        when(orderService.findById(anyLong())).thenThrow(NotFoundException.class);

        // act, assert
        mockMvc.perform(get(OrderController.BASE_ORDER_URL + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test number format exception on string id")
    public void testGetOrderNumberFormatException() throws Exception {

        // arrange

        // act, assert
        mockMvc.perform(get(OrderController.BASE_ORDER_URL + "/aaa")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors[0]", containsString("java.lang.NumberFormatException")));
    }

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

    @Test
    @DisplayName("Test saving an order")
    public void testSave() throws Exception {

        // arrange
        when(orderService.save(any(Order.class))).thenReturn(testOrder);
        OrderDto orderDto = orderDtoMapper.orderToOrderDto(testOrder);

        // act, assert
        MvcResult result = mockMvc.perform(post(OrderController.BASE_URL)
                .content(jsonMapper.writeValueAsString(orderDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testOrder.getId()))
                .andExpect(jsonPath("$.number").value(testOrder.getNumber()))
                .andExpect(jsonPath("$.statusId").value(testOrder.getStatusId()))
                .andExpect(jsonPath("$.paymentId").value(
                        testOrder.getPaymentInformation().getPaymentId()))
                .andExpect(jsonPath("$.amount").value(
                        testOrder.getPaymentInformation().getAmount()))
                .andExpect(jsonPath("$.discountAmount").value(
                        testOrder.getPaymentInformation().getDiscountAmount()))
                .andExpect(jsonPath("$.address.city").value(testOrder.getAddress().getCity()))
                .andExpect(jsonPath("$.address.zip").value(testOrder.getAddress().getZip()))
                .andExpect(jsonPath("$.address.street").value(testOrder.getAddress().getStreet()))
                .andExpect(jsonPath("$.address.nr").value(testOrder.getAddress().getNr()))
                .andExpect(jsonPath("$.orderParts", hasSize(2)))
                .andReturn();

        EntityModel<OrderDto> returnedOrderDtoModel = jsonMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<EntityModel<OrderDto>>() {
                });
        OrderDto createdOrderDto = returnedOrderDtoModel.getContent();

        assertEquals(testOrder, orderDtoMapper.orderDtoToOrder(createdOrderDto));
        verify(orderService, times(1)).save(testOrder);
    }

    @Test
    @DisplayName("Test saving an order with invalid request body")
    public void testSaveValidationException() throws Exception {

        // arrange
        Order order = new Order(testOrder.getNumber(), testOrder.getCustomerId(),
                testOrder.getRetailerId(), testOrder.getPaymentInformation());

        OrderDto orderDto = orderDtoMapper.orderToOrderDto(order);
        orderDto.setStatusId(null);

        // act, assert
        mockMvc.perform(post(OrderController.BASE_URL)
                .content(jsonMapper.writeValueAsString(orderDto))
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors[0]", containsString("Address is mandatory")));
    }

    @Test
    @DisplayName("Test updating the status of an order")
    void updateStatus() throws Exception {

        // arrange
        long id = testOrder.getId();
        String action = "someAction";
        when(orderService.changeStatus(id, action)).thenReturn(testOrder);

        // act, assert
        mockMvc.perform(patch(OrderController.BASE_URL + "/" + id + "/actions/" + action))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        verify(orderService, times(1)).changeStatus(id, action);
    }

    @Test
    @DisplayName("Test order update order illegal status")
    public void updateStatusIllegalStatus() throws Exception {

        // arrange
        long id = testOrder.getId();
        String action = "someAction";
        when(orderService.changeStatus(id, action)).thenThrow(IllegalOrderStatusException.class);

        // act, assert
        mockMvc.perform(patch(OrderController.BASE_URL + "/" + id + "/actions/" + action)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors[0]", containsString("com.alexvait.orderapi.exception.IllegalOrderStatusException")));
        verify(orderService, times(1)).changeStatus(id, action);
    }
}
