package com.ecom.repository.product;

import com.ecom.models.product.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public ProductRepositoryCustomImpl (MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }



    @Override
    public List<Product> findProductsByCategoryAndBrand(String category, String brand , BigDecimal minPrice , BigDecimal maxPrice , String sort ) {
        Criteria criteria = new Criteria();
        criteria.and("category").is(category);
        criteria.and("brand").is(brand);
        criteria.and("price").gte(minPrice).lte(maxPrice);

        Query query = new Query(criteria);

        if ("price_asc".equals(sort)) {
            query.with(Sort.by(Sort.Direction.ASC, "price"));
        } else if ("price_desc".equals(sort)) {
            query.with(Sort.by(Sort.Direction.DESC, "price"));
        } else if ("newest".equals(sort)) {
            query.with(Sort.by(Sort.Direction.DESC, "createdAt"));
        } else if ("oldest".equals(sort)) {
            query.with(Sort.by(Sort.Direction.ASC, "createdAt"));
        }
        return mongoTemplate.find(query , Product.class);
    }
}
