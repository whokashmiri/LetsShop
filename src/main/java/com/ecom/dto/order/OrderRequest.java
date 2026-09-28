package com.ecom.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OrderRequest {
    private List<OrderItemRequest> orderItems;
    private String address;
}
