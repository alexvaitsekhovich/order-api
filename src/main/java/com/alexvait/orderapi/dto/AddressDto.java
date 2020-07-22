package com.alexvait.orderapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDto {

    private Long id;

    @NotBlank(message = "City is mandatory")
    @ApiModelProperty(required = true)
    private String city;

    @NotBlank(message = "Zip code is mandatory, between 3 and 10 digits")
    @ApiModelProperty(required = true)
    private String zip;

    @NotBlank(message = "Street is mandatory")
    @ApiModelProperty(required = true)
    private String street;

    private String nr;
}
