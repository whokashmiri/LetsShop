package com.ecom.service.payment;

import com.ecom.config.MoyasarConfig;
import com.ecom.dto.payment.MoyasarPaymentRequest;
import com.ecom.dto.payment.MoyasarPaymentResponse;
import com.ecom.dto.payment.MoyasarPaymentSource;
import com.ecom.dto.payment.PaymentRequest;
import com.ecom.exceptions.auth.UserNotAuthenticatedException;
import com.ecom.exceptions.order.OrderNotFoundException;
import com.ecom.models.auth.User;
import com.ecom.models.order.Order;
import com.ecom.repository.order.OrderRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentService {

    private final RestClient restClient;
    private final MoyasarConfig moyasarConfig;
    private final OrderRepository orderRepository;

    private PaymentService(RestClient.Builder restClientBuilder ,
                           MoyasarConfig moyasarConfig,
                           OrderRepository orderRepository){
        this.restClient = restClientBuilder.baseUrl(moyasarConfig.getBaseUrl()).build();
        this.moyasarConfig = moyasarConfig;
        this.orderRepository = orderRepository;
    }

    public MoyasarPaymentResponse createPayment(
            PaymentRequest paymentRequest ){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)){
            throw  new UserNotAuthenticatedException("User not authenticated");
        }

        User user = (User) authentication.getPrincipal();



       Order order = orderRepository.findById(paymentRequest.getOrderId()
       ).orElseThrow(() ->
               new OrderNotFoundException("Order not found")
       );

        if (!order.getUserId().equals(user.getId())) {
            throw new OrderNotFoundException("Order not found");
        }

        int amount = order.getTotalAmount().multiply(
                BigDecimal.valueOf(100)
        ).intValueExact();


        MoyasarPaymentRequest moyasarPaymentRequest =
                new MoyasarPaymentRequest();
        moyasarPaymentRequest.setAmount(amount);
        moyasarPaymentRequest.setCurrency("SAR");
        moyasarPaymentRequest.setDescription("LetsShop Order " + order.getId());
        moyasarPaymentRequest.setCallbackUrl("https://example.com/payment/callback");
        moyasarPaymentRequest.setGivenId(UUID.randomUUID().toString());

        MoyasarPaymentSource source = new MoyasarPaymentSource();
        source.setType("token");
        source.setToken(paymentRequest.getToken());
        moyasarPaymentRequest.setSource(source);

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




    public MoyasarPaymentResponse getPayment(String paymentId){
        return restClient.get()
                .uri("/payments/{paymentId}" , paymentId)
                .headers(headers ->
                        headers.setBasicAuth(
                                moyasarConfig.getSecretKey() , ""
                        ))
                .retrieve()
                .body(MoyasarPaymentResponse.class);
    }


}
