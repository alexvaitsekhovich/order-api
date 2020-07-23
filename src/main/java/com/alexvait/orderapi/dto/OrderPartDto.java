package com.alexvait.orderapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPartDto {

    private Long id;

    @NotNull(message = "Item id is mandatory")
    private Long itemId;

    @NotNull(message = "Item name is mandatory")
    private String itemName;

    @NotNull(message = "Item count is mandatory")
    @Positive
    private Integer count;

    @NotNull(message = "Item price is mandatory")
    @PositiveOrZero
    private Integer price;
}
