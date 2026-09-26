package com.ecom.controller.cart;

import com.ecom.dto.cart.CartItemRequest;
import com.ecom.dto.cart.CartResponse;
import com.ecom.service.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }
    @PostMapping
    public ResponseEntity<CartResponse> addItemToCart(  @RequestBody CartItemRequest cartItemRequest){
     CartResponse cartResponse =   cartService.addItemToCart(cartItemRequest);
     return ResponseEntity.ok(cartResponse);

    }

    @PatchMapping
    public ResponseEntity<CartResponse> removeSingleItem(String productId){
        CartResponse cartResponse = cartService.removeSingleItemFromCart(productId);
        return ResponseEntity.ok(cartResponse);
    }
}
