package com.location.address.dto;


import com.location.address.enums.AddressType;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {


    private Long id;


    private String houseNumber;


    private String street;


    private String landmark;


    private String postalCode;


    private AddressType addressType;


    private Long villageId;


    private String villageName;


}