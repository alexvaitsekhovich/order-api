package com.alexvait.orderapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDto extends RepresentationModel<OrderDto> {
    private Long id;

    private String number;

    private Integer statusId;

    @NotNull(message = "Payment id is mandatory")
    @ApiModelProperty(required = true)
    private Integer paymentId;

    @NotNull(message = "Amount is mandatory")
    @ApiModelProperty(value = "Order amount in smallest currency, as integer", required = true)
    private Integer amount;

    private Integer discountAmount;

    @NotNull(message = "Address is mandatory")
    @ApiModelProperty(required = true)
    private AddressDto address;

    @NotNull(message = "An array of order parts is mandatory")
    private List<OrderPartDto> orderParts = new ArrayList<>();
}
