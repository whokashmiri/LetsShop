package com.ecom.dto.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoyasarPaymentSourceResponse {
    private String type;
    private String message;
    private String transactionUrl;
    private String referenceNumber;
}
