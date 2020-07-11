package com.alexvait.orderapi.entity;

import com.alexvait.orderapi.exception.IllegalOrderStatusException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    CREATED(0, "create"),
    APPROVED(1, "approve"),
    DELIVERED(2, "deliver"),
    CANCELLED(-1, "cancel"),
    FAKE(-2, "fake");

    private final int id;
    private final String action;

    private static final Map<String, Integer> idByAction = new HashMap<>();

    static {
        for (OrderStatus e : OrderStatus.values()) {
            idByAction.put(e.getAction(), e.getId());
        }
    }

    public static int getStatusByAction(String action) {
        Integer statusId = idByAction.get(action);

        if (statusId == null) {
            throw new IllegalOrderStatusException("Illegal order action: " + action);
        }

        return statusId;
    }
}
