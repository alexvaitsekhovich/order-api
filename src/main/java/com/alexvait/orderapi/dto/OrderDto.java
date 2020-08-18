package com.alexvait.orderapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotNull(message = "Customer id is mandatory")
    private Long customerId;

    @NotNull(message = "Retailer id is mandatory")
    private Long retailerId;

    private Integer statusId;

    @NotNull(message = "Payment id is mandatory")
    @Positive
    private Integer paymentId;

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount shall be greater than 0")
    private Integer amount;

    @PositiveOrZero
    private Integer discountAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdOn;

    @NotNull(message = "Address is mandatory")
    private AddressDto address;

    @NotNull(message = "An array of order parts is mandatory")
    private List<OrderPartDto> orderParts = new ArrayList<>();
}
