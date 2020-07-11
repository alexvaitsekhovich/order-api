package com.alexvait.orderapi.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

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
    private Integer count;

    @NotNull(message = "Item price is mandatory")
    private Integer price;
}
