package com.ecom.service.payment;

import com.ecom.config.MoyasarConfig;
import com.ecom.dto.payment.MoyasarPaymentRequest;
import com.ecom.dto.payment.MoyasarPaymentResponse;
import com.ecom.dto.payment.MoyasarPaymentSource;
import com.ecom.dto.payment.PaymentRequest;
import com.ecom.exceptions.auth.UserNotAuthenticatedException;
import com.ecom.exceptions.order.OrderNotFoundException;
import com.ecom.exceptions.product.CartQuantityExceededException;
import com.ecom.exceptions.product.ProductNotFoundException;
import com.ecom.models.auth.User;
import com.ecom.models.cart.Cart;
import com.ecom.models.order.Order;
import com.ecom.models.order.OrderItem;
import com.ecom.models.order.OrderStatus;
import com.ecom.models.order.PaymentStatus;
import com.ecom.models.product.Product;
import com.ecom.repository.cart.CartRepository;
import com.ecom.repository.order.OrderRepository;
import com.ecom.repository.product.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final RestClient restClient;
    private final MoyasarConfig moyasarConfig;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public PaymentService(RestClient.Builder restClientBuilder ,
                           MoyasarConfig moyasarConfig,
                           OrderRepository orderRepository,
                           ProductRepository productRepository,
                            CartRepository cartRepository
    ){
        this.restClient = restClientBuilder.baseUrl(moyasarConfig.getBaseUrl()).build();
        this.moyasarConfig = moyasarConfig;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
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
        moyasarPaymentRequest.setCallbackUrl(moyasarConfig.getCallbackUrl());
        moyasarPaymentRequest.setGivenId(UUID.randomUUID().toString());

        MoyasarPaymentSource source = new MoyasarPaymentSource();
        source.setType("token");
        source.setToken(paymentRequest.getToken());
        moyasarPaymentRequest.setSource(source);

        MoyasarPaymentResponse response = restClient.post().uri("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers ->
                        headers.setBasicAuth(
                                moyasarConfig.getPublishableKey(),""
                        )
                        )
                .body(moyasarPaymentRequest)
                .retrieve()
                .body(MoyasarPaymentResponse.class);

        order.setMoyasarPaymentId(response.getId());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return response;
    }


    private void reduceProductStock(Order order){
        for(OrderItem orderItem : order.getOrderItemList()){
            Product product = productRepository
                    .findById(orderItem.getProductId())
                    .orElseThrow(() ->
                            new ProductNotFoundException(
                                    "Product not found: " + orderItem.getProductId()
                            ));
            if (orderItem.getQuantity() > product.getQuantity()){
                throw  new CartQuantityExceededException(
                        "Not enough stock" + product.getName()
                );
            }

            int remainingQuantity =  product.getQuantity() -  orderItem.getQuantity();

            product.setQuantity(remainingQuantity);
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);

        }
    }


    private void removePurchasedItemsFromCart(Order order,
                                              String userId){
       Cart cart =  cartRepository.findByUserId(userId).orElse(null);

       if (cart == null || cart.getCartItems() == null){
           return;
       }
       for (OrderItem orderItem : order.getOrderItemList()){
           String productId =  orderItem.getProductId();
           cart.getCartItems().removeIf(
                   cartItem -> cartItem.getProductId().equalsIgnoreCase(productId)
           );
           cart.setUpdatedAt(LocalDateTime.now());
           cartRepository.save(cart);
       }
    }




    public MoyasarPaymentResponse getPayment(String paymentId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication==null || !((authentication.getPrincipal()) instanceof User)){
            throw new UserNotAuthenticatedException("User not authenticated");
        }

        User user  = (User) authentication.getPrincipal();

       String userId =  user.getId();


        MoyasarPaymentResponse moyasarPaymentResponse =  restClient.get()
                .uri("/payments/{paymentId}" , paymentId)
                .headers(headers ->
                        headers.setBasicAuth(
                                moyasarConfig.getSecretKey() , ""
                        ))
                .retrieve()
                .body(MoyasarPaymentResponse.class);

        Order order = orderRepository.findByMoyasarPaymentId(paymentId)
                .orElseThrow(()->
                        new OrderNotFoundException("Order not associated with payment"));

        if (!order.getUserId().equals(userId)){
            throw new OrderNotFoundException("Order not found");
        }


        if (!"paid".equalsIgnoreCase(moyasarPaymentResponse.getStatus())){
            return moyasarPaymentResponse;
        }
        if (!"SAR".equalsIgnoreCase(moyasarPaymentResponse.getCurrency())){
            throw  new IllegalStateException("Payment currency does not match");
        }
        int expectedAmount = order.getTotalAmount()
                .multiply(BigDecimal.valueOf(100))
                .intValueExact();

        if (moyasarPaymentResponse.getAmount() == null ||
            moyasarPaymentResponse.getAmount() != expectedAmount
        ){
            throw new IllegalStateException("Payment amount does not match Order total");
        }

        if (order.getPaymentStatus() == PaymentStatus.PAID){
            return  moyasarPaymentResponse;
        }
        reduceProductStock(order);
        removePurchasedItemsFromCart(order,user.getId());

        order.setPaymentStatus(PaymentStatus.PAID);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
        return moyasarPaymentResponse;

    }




}
