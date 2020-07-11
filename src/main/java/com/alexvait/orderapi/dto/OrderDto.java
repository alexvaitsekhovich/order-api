package com.alexvait.orderapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;

    @NotBlank
    @NonNull
    private String number;

    @NotNull(message = "Status id is mandatory")
    @NonNull
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

    public OrderDto(Long id, String number, int statusId) {
        this.id = id;
        this.number = number;
        this.statusId = statusId;
    }

    public void addOrderPart(OrderPartDto orderPartDto) {
        orderParts.add(orderPartDto);
    }
}
