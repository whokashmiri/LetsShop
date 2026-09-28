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

    @DeleteMapping("/item")
    public ResponseEntity<CartResponse> removeSingleItem(@RequestParam  String productId){
        CartResponse cartResponse = cartService.removeSingleItemFromCart(productId);
        return ResponseEntity.ok(cartResponse);
    }

    @PatchMapping("/increase")
    public ResponseEntity<CartResponse> increaseQuantity(@RequestParam String productId){
     CartResponse cartResponse =    cartService.increaseQuantity(productId);
     return ResponseEntity.ok(cartResponse);
    }
    @PatchMapping("/decrease")
    public ResponseEntity<CartResponse> decreaseQuantity(@RequestParam String productId){
        CartResponse cartResponse =    cartService.decreaseQuantity(productId);
        return ResponseEntity.ok(cartResponse);
    }
}
