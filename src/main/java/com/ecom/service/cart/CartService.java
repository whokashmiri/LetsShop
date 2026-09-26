
package com.ecom.service.cart;
import com.ecom.dto.cart.CartItemRequest;
import com.ecom.dto.cart.CartItemResponse;
import com.ecom.dto.cart.CartResponse;
import com.ecom.models.cart.Cart;
import com.ecom.models.cart.CartItem;
import com.ecom.repository.cart.CartRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    public CartResponse addItemToCart(String userId , CartItemRequest cartItemRequest){

     Optional <Cart> cartOptional =  cartRepository.findByUserId(userId);
     Cart cart = new Cart();
     if (cartOptional.isPresent()){
         cart =  cartOptional.get();
     }else {
         cart.setUserId(userId);
         cart.setCartItems(new ArrayList<>());
         LocalDateTime now = LocalDateTime.now();
         cart.setCreatedAt(now);
         cart.setUpdatedAt(now);
     }

        CartItem cartItem = new CartItem();
     cartItem.setProductId(cartItemRequest.getProductId());
     cartItem.setQuantity(cartItemRequest.getQuantity());


     cart.getCartItems().add(cartItem);

     cart.setUpdatedAt(LocalDateTime.now());

     Cart savedCart = cartRepository.save(cart);

     CartResponse cartResponse = new CartResponse();
     cartResponse.setId(savedCart.getId());
     cartResponse.setCreatedAt(savedCart.getCreatedAt());
     cartResponse.setUpdatedAt(savedCart.getUpdatedAt());

     List<CartItemResponse> cartItemResponses = new ArrayList<>();

     for (CartItem item : savedCart.getCartItems()){

         CartItemResponse cartItemResponse = new CartItemResponse();
         cartItemResponse.setProductId(item.getProductId());
         cartItemResponse.setQuantity(item.getQuantity());

         cartItemResponses.add(cartItemResponse);

     }
     cartResponse.setCartItems(cartItemResponses);
     return cartResponse;

    }

}

