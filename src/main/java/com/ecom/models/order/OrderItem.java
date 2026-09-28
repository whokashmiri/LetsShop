package com.ecom.models.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItem {
    private String productId;
    private int quantity;
    private BigDecimal price;
}
