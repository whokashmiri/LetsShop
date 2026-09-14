package com.ecom.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class ProductRequest {
    private String name;
    private String category;
    private BigDecimal price;
    private String description;
    private int quantity;
    private String brand;
    private List<String> images;
}
