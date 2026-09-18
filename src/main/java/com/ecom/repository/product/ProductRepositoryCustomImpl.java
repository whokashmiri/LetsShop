package com.ecom.repository.product;

import com.ecom.models.product.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public ProductRepositoryCustomImpl (MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }



    @Override
    public List<Product> findProductsByCategoryAndBrand(String category, String brand , BigDecimal minPrice , BigDecimal maxPrice , String sort) {
        Criteria criteria = new Criteria();
        criteria.and("category").is(category);
        criteria.and("brand").is(brand);
        criteria.and("price").gte(minPrice);
        criteria.and("price").lte(maxPrice);
        Query query = new Query(criteria);

        if (sort.equals("price_asc")){
            query.with(Sort.by(Sort.Direction.ASC , "price"));
        } if (sort.equals("price_dsc")){
            query.with(Sort.by(Sort.Direction.DESC , "price"));
        }

        return mongoTemplate.find(query , Product.class);
    }
}
