package com.alexvait.orderapi.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDto {
    private Long id;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Zip code is mandatory, between 3 and 10 digits")
    private String zip;

    @NotBlank(message = "Street is mandatory")
    private String street;

    private String nr;
}
