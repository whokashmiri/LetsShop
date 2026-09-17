package com.ecom.repository.product;

import com.ecom.models.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product , String> {
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findProductByCategory(String category);
}
