package com.ecom.models.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document("orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private List<OrderItem> orderItemList;
    private String address;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
    private String moyasarPaymentId;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
