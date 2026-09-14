package com.ecom.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String name;
    private String email;
    private String phone;
}
