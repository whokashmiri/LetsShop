
package com.ecom.service.cart;
import com.ecom.dto.cart.CartItemRequest;
import com.ecom.dto.cart.CartItemResponse;
import com.ecom.dto.cart.CartResponse;
import com.ecom.exceptions.auth.UserNotAuthenticatedException;
import com.ecom.exceptions.product.ProductNotFoundException;
import com.ecom.models.auth.User;
import com.ecom.models.cart.Cart;
import com.ecom.models.cart.CartItem;
import com.ecom.models.product.Product;
import com.ecom.repository.cart.CartRepository;
import com.ecom.repository.product.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartService {

    private final CartRepository cartRepository;
    public final ProductRepository productRepository;

    public CartService(CartRepository cartRepository , ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }


    public CartResponse addItemToCart(CartItemRequest cartItemRequest){

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null || !(authentication.getPrincipal() instanceof User)){
          throw new UserNotAuthenticatedException("User not authenticated");
      }
     User user = (User) authentication.getPrincipal();
      String userId = user.getId();

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

      productRepository.findById(cartItemRequest.getProductId()).orElseThrow(() ->
             new ProductNotFoundException("Product not Found"));

     CartItem existingItem = null;
     for (CartItem item : cart.getCartItems()){
         if (item.getProductId().equals(cartItemRequest.getProductId())){
             existingItem = item;
             break;

         }
     }
         if (existingItem != null){
             existingItem.setQuantity(existingItem.getQuantity() + cartItemRequest.getQuantity());

         }else {

             CartItem cartItem = new CartItem();
             cartItem.setProductId(cartItemRequest.getProductId());
             cartItem.setQuantity(cartItemRequest.getQuantity());


             cart.getCartItems().add(cartItem);

         }


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

