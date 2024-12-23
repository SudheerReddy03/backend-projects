package com.address.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private int addressId;
    private String cityName;
    private String district;
    private String state;
    private int zipcode;
    private int empId;

}
