package com.ecom.dto.address;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressUpdateRequest {

    private String fullName;

    private String phone;

    private String pinCode;

    private String country;

    private String city;

    private String streetAndHouse;
}
