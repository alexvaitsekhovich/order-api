package com.alexvait.orderapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;

    @NotBlank
    private String number;

    private Integer statusId;

    @NotNull(message = "Payment id is mandatory")
    private Integer paymentId;

    @NotNull(message = "Amount is mandatory")
    private Integer amount;

    private Integer discountAmount;

    @NotNull(message = "Address is mandatory")
    private AddressDto address;

    @NotNull(message = "An array of order parts is mandatory")
    private List<OrderPartDto> orderParts = new ArrayList<>();
}
