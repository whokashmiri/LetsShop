package com.ecom.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoyasarPaymentSourceResponse {
    private String type;
    private String message;

    @JsonProperty("transaction_url")
    private String transactionUrl;

    @JsonProperty("reference_number")
    private String referenceNumber;
}
