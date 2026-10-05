package com.ecom.service.kafka;

import com.ecom.dto.order.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(
            topics = "orders",
            groupId = "letshop-group"
    )
    public void consumeOrder(OrderCreatedEvent event) {

        System.out.println("Received order event");
        System.out.println("Event Type: " + event.getEventType());
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("User ID: " + event.getUserId());
        System.out.println("Total Amount: " + event.getTotalAmount());
    }
}