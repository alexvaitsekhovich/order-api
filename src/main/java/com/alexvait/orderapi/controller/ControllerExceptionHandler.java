package com.alexvait.orderapi.controller;

import com.alexvait.orderapi.error.ApiError;
import com.alexvait.orderapi.exception.IllegalOrderStatusException;
import com.alexvait.orderapi.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFound(NotFoundException exception,
                               HttpServletRequest httpRequest) {

        log.error(String.format("NotFoundException: %s at URL: %s",
                exception.getMessage(), httpRequest.getRequestURI()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map<String, Object>> handleNumberFormatException(NumberFormatException exception,
                                                                           HttpServletRequest httpRequest) {

        log.error(String.format("NumberFormatException: %s at URL: %s",
                exception.getMessage(), httpRequest.getRequestURI()));

        return constructResponseEntity(HttpStatus.BAD_REQUEST, exception.toString(), httpRequest.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, HttpServletRequest httpRequest) {

        log.error(String.format("MethodArgumentNotValidException: %s at URL: %s",
                exception.getMessage(), httpRequest.getRequestURI()));

        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return constructResponseEntity(HttpStatus.BAD_REQUEST, errors, httpRequest.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalOrderStatusException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalOrderStatusException(IllegalOrderStatusException exception,
                                                                                 HttpServletRequest httpRequest) {

        log.error(String.format("IllegalOrderStatusException: %s at URL: %s",
                exception.getMessage(), httpRequest.getRequestURI()));

        return constructResponseEntity(HttpStatus.BAD_REQUEST, exception.toString(), httpRequest.getRequestURI());
    }

    private ResponseEntity<Map<String, Object>> constructResponseEntity(HttpStatus httpStatus, String error, String uri) {
        return constructResponseEntity(httpStatus, Collections.singletonList(error), uri);
    }

    private ResponseEntity<Map<String, Object>> constructResponseEntity(HttpStatus httpStatus, List<String> errors, String uri) {
        ApiError apiError = new ApiError(httpStatus);
        apiError.setErrors(errors);
        apiError.setPath(uri);
        return new ResponseEntity<>(apiError.getErrorBody(), httpStatus);
    }
}