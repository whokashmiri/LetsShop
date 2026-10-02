package com.ecom.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoyasarPaymentRequest {
    private Integer amount ;
    private String description;
    private String currency;

    @JsonProperty("callback_url")
    private String callbackUrl;

    @JsonProperty("given_id")
    private String givenId;

    private MoyasarPaymentSource source;
}
