package com.alexvait.orderapi.exception;

public class IllegalOrderStatusException extends RuntimeException {

    public IllegalOrderStatusException(String message) {
        super(message);
    }
}
