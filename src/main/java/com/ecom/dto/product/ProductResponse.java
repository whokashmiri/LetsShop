package com.ecom.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Setter
@Getter
public class ProductResponse {
    private String id;
    private String name;
    private String category;
    private BigDecimal price;
    private String description;
    private int quantity;
    private String brand;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
