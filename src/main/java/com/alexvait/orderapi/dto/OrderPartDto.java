package com.alexvait.orderapi.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(required = true)
    private Long itemId;

    @NotNull(message = "Item name is mandatory")
    @ApiModelProperty(required = true)
    private String itemName;

    @NotNull(message = "Item count is mandatory")
    @ApiModelProperty(required = true)
    @Positive
    private Integer count;

    @NotNull(message = "Item price is mandatory")
    @ApiModelProperty(value = "price for a single item, will be multiplied with the count value", required = true)
    @PositiveOrZero
    private Integer price;
}
