package com.ecom.models.cart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
public class CartItem {


    private String productId;
    private Integer quantity;

}
