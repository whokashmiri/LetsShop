package com.ecom.repository.product;

import com.ecom.models.product.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Product> findProductsByCategoryAndBrand(String category, String brand , BigDecimal minPrice , BigDecimal maxPrice , String sort  , int page , int size ) {
        Criteria criteria = new Criteria();
        if (category != null && !category.isBlank() ){
            criteria.and("category").is(category);
        }
        if(brand != null && !brand.isBlank()){
            criteria.and("brand").is(brand);
        }
        if (minPrice != null) {
            criteria.and("price").gte(minPrice);
        }

        if (maxPrice != null) {
            criteria.and("price").lte(maxPrice);
        }
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
        Pageable pageable = PageRequest.of(page,size);
        query.with(pageable);
        return mongoTemplate.find(query , Product.class);
    }
}
