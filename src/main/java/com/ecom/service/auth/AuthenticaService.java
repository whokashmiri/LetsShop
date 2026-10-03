package com.ecom.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.print.DocFlavor;
import java.util.Map;

@Service
public class AuthenticaService {

    private final RestClient restClient;

    @Value("${authentica.api-key}")
    private String apikey;

    public AuthenticaService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.authentica.sa")
                .build();
    }

    public void sendOtp(String phone) {

        Map<String, String> requestBody = Map.of(
                "method", "sms",
                "phone", phone
        );

        restClient.post()
                .uri("/api/v2/send-otp")
                .header("X-Authorization", apikey)
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toBodilessEntity();
    }

 public void verifyOtp(String phone , String otp){
        Map<String , String > requestBody =  Map.of(
                "phone" , phone,
                "otp" , otp
        );
        restClient.post()
                .uri("/api/v2/verify-otp")
                .header("X-Authorization" , apikey)
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .toBodilessEntity();
 }
}