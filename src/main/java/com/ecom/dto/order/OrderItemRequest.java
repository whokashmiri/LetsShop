package com.ecom.dto.order;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderItemRequest {

    private String productId;
    private int quantity;

}
