package com.location.address.dto;


import com.location.address.enums.AddressType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressRequestDto {


    @NotBlank(message = "House number is required")
    private String houseNumber;


    @NotBlank(message = "Street is required")
    private String street;


    private String landmark;


    @NotBlank(message = "Postal code is required")
    private String postalCode;


    @NotNull(message = "Address type is required")
    private AddressType addressType;


    @NotNull(message = "Village is required")
    private Long villageId;

}