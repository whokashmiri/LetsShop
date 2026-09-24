package com.ecom.service.review;

import com.ecom.dto.review.ReviewResponse;
import com.ecom.models.review.Review;
import com.ecom.repository.review.ReviewRepository;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    public final ReviewRepository reviewRepository;
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository =  reviewRepository;
    }

    public List<ReviewResponse> getReviewsOfProduct(String productId){
       List<Review> reviews =  reviewRepository.findByProductId(productId);
       if (reviews.isEmpty()){
           throw new RuntimeException("No reviews found");
       }
       List<ReviewResponse>  reviewResponseList =  new ArrayList<>();
       for (Review review :reviews){
         ReviewResponse reviewResponse =  new ReviewResponse();
         reviewResponse.setId(review.getId());
         reviewResponse.setComment(review.getComment());
         reviewResponse.setUserId(review.getUserId());
         reviewResponse.setProductId(review.getProductId());
         reviewResponse.setStars(review.getStars());
         reviewResponse.setCreatedAt(review.getCreatedAt());
         reviewResponse.setUpdatedAt(review.getUpdatedAt());
         reviewResponseList.add(reviewResponse);
       }
       return reviewResponseList;
    }
}
