package com.alexvait.orderapi.integration;

import com.alexvait.orderapi.dto.OrderDto;

public interface OrderTransmitter {
    void transmit(OrderDto order);
}
