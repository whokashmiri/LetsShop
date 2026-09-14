package com.ecom.models.product;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

public class Product {
    @Id
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
