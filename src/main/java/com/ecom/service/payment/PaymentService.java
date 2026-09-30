package com.ecom.service.payment;

import com.ecom.config.MoyasarConfig;
import com.ecom.dto.payment.MoyasarPaymentRequest;
import com.ecom.dto.payment.MoyasarPaymentResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class PaymentService {

    private final RestClient restClient;
    private final MoyasarConfig moyasarConfig;

    private PaymentService(RestClient.Builder restClientBuilder , MoyasarConfig moyasarConfig){
        this.restClient = restClientBuilder.baseUrl(moyasarConfig.getBaselUrl()).build();
        this.moyasarConfig = moyasarConfig;
    }

    public MoyasarPaymentResponse createPayment(MoyasarPaymentRequest moyasarPaymentRequest){
        return restClient.post().uri("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers ->
                        headers.setBasicAuth(
                                moyasarConfig.getPublishableKey(),""
                        )
                        )
                .body(moyasarPaymentRequest)
                .retrieve()
                .body(MoyasarPaymentResponse.class);
    }

    private String createBasicAuth(String apiKey) {
        String credentials = apiKey + ":";

        return Base64.getEncoder().encodeToString(
                credentials.getBytes(StandardCharsets.UTF_8)
        );
    }


}
