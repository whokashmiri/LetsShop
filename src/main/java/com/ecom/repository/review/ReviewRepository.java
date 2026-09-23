package com.ecom.repository.review;

import com.ecom.models.review.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByProductId(String productId);
}