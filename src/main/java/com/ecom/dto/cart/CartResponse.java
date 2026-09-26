package com.ecom.dto.cart;

import com.ecom.models.cart.CartItem;


import java.time.LocalDateTime;
import java.util.List;

public class CartResponse {

    private String id;
    private String userId;
    private List<CartItem> cartItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
