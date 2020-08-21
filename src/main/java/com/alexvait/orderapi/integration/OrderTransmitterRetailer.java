package com.alexvait.orderapi.integration;

import com.alexvait.orderapi.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderTransmitterRetailer implements OrderTransmitter {
    @Override
    public void transmit(OrderDto order) {
        // TODO
    }
}
