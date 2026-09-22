package com.ecom.dto.product;

import com.ecom.models.category.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class ProductRequest {

    @NotBlank
    private String name;

    private Category category;

    @NotNull
    @Positive
    private BigDecimal price;

    private String description;

    @PositiveOrZero
    private Integer quantity;

    @NotBlank
    private String brand;

    private List<String> images;
}
