package com.ecom.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderCreatedEvent {

    private String eventType;
    private String orderId;
    private String userId;
    private BigDecimal totalAmount;
}