package com.ecom.controller.kafka;

import com.ecom.service.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaTestController {
    private final OrderProducer orderProducer;

    public KafkaTestController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;

    }

    @PostMapping("/test-order")
    public String testOrder() {

        orderProducer.sendOrderCreated("ORDER_CREATED");

        return "Order event sent to Kafka";
    }
}
