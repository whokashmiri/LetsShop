package com.ecom.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoyasarPaymentRequest {
    private Integer amount ;
    private String description;

    @JsonProperty("callback_url")
    private String callBackUrl;

    @JsonProperty("given_id")
    private String given;

    private MoyasarPaymentSource source;
}
