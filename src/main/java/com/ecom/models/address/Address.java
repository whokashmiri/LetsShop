package com.ecom.models.address;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Getter
@Setter
@Document("addresses")
public class Address {
    @Id
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
