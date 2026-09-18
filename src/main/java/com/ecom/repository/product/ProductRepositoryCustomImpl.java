package com.ecom.repository.product;

import com.ecom.models.product.Product;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public ProductRepositoryCustomImpl (MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }



    @Override
    public List<Product> findProductsByCategoryAndBrand(String category, String brand) {
        Criteria criteria = new Criteria();
        criteria.and("category").is(category);
        criteria.and("brand").is(brand);
        return null;
    }
}
