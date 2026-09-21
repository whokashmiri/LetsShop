package com.ecom.repository.product;

import com.ecom.models.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends MongoRepository<Product , String> , ProductRepositoryCustom{
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findProductByCategory(String category);

}
