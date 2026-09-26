package com.ecom.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponse {
    private String productId;
    private Integer quantity;
}
