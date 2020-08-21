package com.alexvait.orderapi.integration.rabbitmessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Message container, specifying the delay time, routing key for the destination queue and
 * the message that shall be delivered with the specified delay.
 */
public class DelayedMessage {
    private long delayTime;
    private String routingKey;
    private String payload;

    public DelayedMessage() {
    }

    public DelayedMessage(long delayTime, String routingKey, String payload) {
        this.delayTime = delayTime;
        this.routingKey = routingKey;
        this.payload = payload;
    }

    public static DelayedMessage fromJsonString(String jsonMessage) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonMessage, DelayedMessage.class);
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "MqMessage{"
                + "deliveryTime='" + delayTime + '\''
                + ", routingKey='" + routingKey + '\''
                + ", payload='" + payload + '\''
                + '}';
    }
}
