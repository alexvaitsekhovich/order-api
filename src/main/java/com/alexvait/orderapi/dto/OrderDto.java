package com.alexvait.orderapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderDto {

    private Long id;

    private String number;

    private Integer statusId;

    @NotNull(message = "Payment id is mandatory")
    @ApiModelProperty(required = true)
    @Positive
    private Integer paymentId;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount shall be greater than 0")
    @ApiModelProperty(value = "Order amount in smallest currency, as integer", required = true)
    private Integer amount;

    @PositiveOrZero
    private Integer discountAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdOn;

    @NotNull(message = "Address is mandatory")
    @ApiModelProperty(required = true)
    private AddressDto address;

    @NotNull(message = "An array of order parts is mandatory")
    private List<OrderPartDto> orderParts = new ArrayList<>();
}
