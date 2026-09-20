package com.ecom.repository.product;

import com.ecom.models.product.Product;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepositoryCustom {

    Page<Product> findProductsByCategoryAndBrand(String category , String brand , BigDecimal minPrice , BigDecimal maxPrice , String sort , int page , int size);
}
