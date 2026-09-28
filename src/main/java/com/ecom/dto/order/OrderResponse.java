package com.ecom.dto.order;

import com.ecom.models.order.OrderItem;
import com.ecom.models.order.OrderStatus;
import com.ecom.models.order.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Setter
@Getter
public class OrderResponse {
    private String id;
    private String userId;
    private List<OrderItem> orderItemList;
    private String address;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
