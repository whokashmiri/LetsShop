package com.ecom.models.cart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document("cart")
public class CartItem {
    @Id
    private String id;
    private String cartId;
    private String productId;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
