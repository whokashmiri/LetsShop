package com.ecom.service.kafka;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final KafkaTemplate<String , String> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String , String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreated(String message){
        kafkaTemplate.send("orders" , message);
    }
}
