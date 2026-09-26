package com.ecom.dto.cart;

import com.ecom.models.cart.CartItem;

import java.util.List;

public class CartRequest {
    private String userId;
    private List<CartItem> cartItems;
}
