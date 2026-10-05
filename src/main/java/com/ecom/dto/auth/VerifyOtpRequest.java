package com.ecom.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {
    private String phone;
    private String otp;

}
