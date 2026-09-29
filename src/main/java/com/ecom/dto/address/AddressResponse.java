package com.ecom.dto.address;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddressResponse {
    private String id;
    private String userId;

    private String fullName;

    private String phone;

    private String country;

    private String city;

    private String streetAndHouse;

    private String pinCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
