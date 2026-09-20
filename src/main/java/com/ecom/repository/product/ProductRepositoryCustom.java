package com.ecom.repository.product;

import com.ecom.models.product.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findProductsByCategoryAndBrand(String category , String brand , BigDecimal minPrice , BigDecimal maxPrice , String sort , int page , int size);
}
