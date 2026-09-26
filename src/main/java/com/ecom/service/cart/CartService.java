
package com.ecom.service.cart;
import com.ecom.repository.cart.CartRepository;
import org.springframework.stereotype.Service;



@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


}

