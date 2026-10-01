package com.ecom.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("amount_format")
    private String amountFormat;

    @JsonProperty("fee_format")
    private String feeFormat;

    @JsonProperty("refunded_format")
    private String refundedFormat;

    @JsonProperty("captured_format")
    private String capturedFormat;

    @JsonProperty("callback_url")
    private String callbackUrl;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;


    private Map<String, String> metadata;
    private MoyasarPaymentSourceResponse source;

}
