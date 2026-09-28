package com.ecom.controller.order;

import com.ecom.dto.order.OrderRequest;
import com.ecom.dto.order.OrderResponse;
import com.ecom.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")
public class OrderController {

    private OrderService orderService;
    public OrderController ( OrderService orderService){
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody  OrderRequest orderRequest){
       OrderResponse orderResponse = orderService.createOrder(orderRequest);
       return ResponseEntity.ok(orderResponse);
    }
}
