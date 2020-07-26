package com.alexvait.orderapi.dto;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class OrderDtoPagedList extends PageImpl<OrderDto> implements Serializable {

    public OrderDtoPagedList(List<OrderDto> content) {
        super(content);
    }

    public OrderDtoPagedList(List<OrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
