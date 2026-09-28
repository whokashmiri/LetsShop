package com.ecom.service.order;

import com.ecom.dto.order.OrderResponse;
import com.ecom.dto.order.OrderItemRequest;
import com.ecom.dto.order.OrderRequest;
import com.ecom.exceptions.auth.UserNotAuthenticatedException;
import com.ecom.models.auth.User;
import com.ecom.models.order.Order;
import com.ecom.repository.order.OrderRepository;
import com.ecom.repository.product.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    public OrderService(OrderRepository orderRepository ,  ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderResponse createOrder(OrderRequest orderRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)){
            throw new UserNotAuthenticatedException("User not authenticated");
        }
        User user = (User) authentication.getPrincipal();
       String userId =  user.getId();

        Order order = new Order();
        order.setUserId(userId);
        order.setAddress(orderRequest.getAddress());





    }
}
