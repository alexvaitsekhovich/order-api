package com.alexvait.orderapi.integration;

import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.integration.rabbitmessage.DelayedMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class OrderTransmitterAmqp implements OrderTransmitter {

    @Value("${alexvait.orderReview.orderReviewDelayTime}")
    private long orderReviewDelayTime;

    @Value("${alexvait.orderReview.delayExchangeName}")
    private String delayExchangeName;

    @Value("${alexvait.orderReview.reviewEmailRoutingKey}")
    private String reviewEmailRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public OrderTransmitterAmqp(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void transmit(OrderDto order) {

        log.info("Sending message to order review delay queue for order #" + order.getId());

        DelayedMessage delayedMessage = new DelayedMessage(orderReviewDelayTime, reviewEmailRoutingKey, String.valueOf(order.getId()));

        try {
            String jsonMessage = delayedMessage.toJsonString();
            rabbitTemplate.convertAndSend(delayExchangeName, "", jsonMessage.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
