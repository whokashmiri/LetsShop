package com.ecom.dto.address;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class AddressRequest {


    @NotEmpty
    @Size(min = 5)
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Invalid phone number format"
    )
    private String phone;

    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    @NotEmpty
    private String streetAndHouse;

    @NotEmpty
    private String pinCode;

}
