package com.ecom.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Setter
@Getter
public class ProductResponse {
    private String id;
    @NotBlank
    private String name;

    private String category;

    @NotNull
    @Positive
    private BigDecimal price;

    private String description;

    @PositiveOrZero
    private Integer quantity;

    @NotBlank
    private String brand;

    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
