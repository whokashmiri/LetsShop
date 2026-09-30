package com.ecom.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MoyasarPaymentResponse {
    private String id;
    private String status;
    private Integer amount;
    private Integer fee;
    private String currency;
    private Integer refunded;
    private Integer captured;
    private String description;
    private String amountFormat;
    private String feeFormat;
    private String refundedFormat;
    private String capturedFormat;
    private String callbackUrl;
    private String createdAt;
    private String updatedAt;
    private Map<String, String> metadata;
    private MoyasarPaymentSourceResponse source;

}
