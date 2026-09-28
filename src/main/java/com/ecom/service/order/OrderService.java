package com.ecom.service.order;

import com.ecom.dto.order.OrderResponse;
import com.ecom.dto.order.OrderItemRequest;
import com.ecom.dto.order.OrderRequest;
import com.ecom.exceptions.auth.UserNotAuthenticatedException;
import com.ecom.exceptions.product.CartQuantityExceededException;
import com.ecom.exceptions.product.ProductNotFoundException;
import com.ecom.models.auth.User;
import com.ecom.models.order.Order;
import com.ecom.models.order.OrderItem;
import com.ecom.models.order.OrderStatus;
import com.ecom.models.order.PaymentStatus;
import com.ecom.models.product.Product;
import com.ecom.repository.order.OrderRepository;
import com.ecom.repository.product.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    public OrderService(OrderRepository orderRepository ,  ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new UserNotAuthenticatedException("User not authenticated");
        }
        User user = (User) authentication.getPrincipal();
        String userId = user.getId();

        Order order = new Order();
        order.setUserId(userId);
        order.setAddress(orderRequest.getAddress());

        List<OrderItem> orderItems = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
            Product product = productRepository.findById(itemRequest.getProductId()).orElseThrow(() ->
                    new ProductNotFoundException(" product not found " + itemRequest.getProductId()
                    )
            );

            if (itemRequest.getQuantity() > product.getQuantity()) {
                throw new CartQuantityExceededException(
                        "Requested quantity exceeds available stock for products : " + product.getName()

                );
            }

            OrderItem orderItem = new OrderItem();

            orderItem.setProductId(product.getId());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);

            BigDecimal itemTotal = product.getPrice().multiply(

                    BigDecimal.valueOf(
                            itemRequest.getQuantity()
                    )
            );
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setOrderItemList(orderItems);
        order.setTotalAmount(totalAmount);

        order.setPaymentStatus(PaymentStatus.PROCESSING);

        order.setOrderStatus(OrderStatus.PROCESSING);

        LocalDateTime now = LocalDateTime.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        orderRepository.save(order);

        OrderResponse response = new OrderResponse();

        response.setId(order.getId());

        response.setUserId(order.getUserId());

        response.setOrderItemList(order.getOrderItemList());

        response.setAddress(order.getAddress());

        response.setTotalAmount(order.getTotalAmount());

        response.setPaymentStatus(order.getPaymentStatus());

        response.setOrderStatus(order.getOrderStatus());

        response.setCreatedAt(order.getCreatedAt());

        response.setUpdatedAt(order.getUpdatedAt());

        return response;


    }



}
