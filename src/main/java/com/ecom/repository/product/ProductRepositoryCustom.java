package com.ecom.repository.product;

import com.ecom.models.product.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findProductsByCategoryAndBrand(String category , String brand);
}
