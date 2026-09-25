package com.ecom.models.cart;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document("carts")
public class Cart {
    @Id
    private String id;
    private String userId;
    private List<CartItem> cartItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
