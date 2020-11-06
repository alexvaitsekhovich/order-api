package com.alexvait.orderapi.integration.rabbitmessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test the delayed message object")
class DelayedMessageTest {

    @Test
    @DisplayName("Test deserialization from a json string")
    void testFromJsonString() throws JsonProcessingException {
        int delayTime = new Random(System.currentTimeMillis()).nextInt(1000);
        String routingKey = RandomStringUtils.randomAlphabetic(50);
        String payload = RandomStringUtils.randomAlphabetic(300);

        String jsonMessage = String.format("{\"delayTime\":%d,\"routingKey\":\"%s\",\"payload\":\"%s\"}",
                delayTime, routingKey, payload);
        DelayedMessage message = DelayedMessage.fromJsonString(jsonMessage);

        assertEquals(delayTime, message.getDelayTime());
        assertEquals(routingKey, message.getRoutingKey());
        assertEquals(payload, message.getPayload());
    }

    @Test
    @DisplayName("Test serialization to json string")
    void testToJsonString() throws JsonProcessingException {
        int delayTime = new Random(System.currentTimeMillis()).nextInt(1000);
        String routingKey = RandomStringUtils.randomAlphabetic(50);
        String payload = RandomStringUtils.randomAlphabetic(300);

        DelayedMessage message = new DelayedMessage(delayTime, routingKey, payload);
        String expectedMessage = String.format("{\"delayTime\":%d,\"routingKey\":\"%s\",\"payload\":\"%s\"}",
                delayTime, routingKey, payload);

        assertEquals(expectedMessage, message.toJsonString());
    }
}