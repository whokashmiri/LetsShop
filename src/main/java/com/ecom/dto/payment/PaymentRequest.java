package com.ecom.dto.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    @NotBlank
    private String orderId;
    @NotBlank
    private String token;
}
