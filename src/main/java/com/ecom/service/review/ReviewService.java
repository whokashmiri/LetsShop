package com.ecom.service.review;

import com.ecom.dto.review.ReviewRequest;
import com.ecom.dto.review.ReviewResponse;
import com.ecom.exceptions.product.ProductNotFoundException;
import com.ecom.models.auth.User;
import com.ecom.models.product.Product;
import com.ecom.models.review.Review;
import com.ecom.repository.auth.UserRepository;
import com.ecom.repository.product.ProductRepository;
import com.ecom.repository.review.ReviewRepository;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReviewService {
    public final ReviewRepository reviewRepository;
    public final ProductRepository productRepository;
    public final UserRepository userRepository;
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository , UserRepository userRepository){
        this.reviewRepository =  reviewRepository;
        this.productRepository = productRepository;
        this.userRepository= userRepository;
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

    public ReviewResponse createReviewOfProduct(
            String productId,
            ReviewRequest reviewRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("No product found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("User is not authenticated");
        }

      User user = (User)  authentication.getPrincipal();

        Review review = new Review();

        review.setProductId(product.getId());
        review.setUserId(user.getId());
        review.setComment(reviewRequest.getComment());
        review.setStars(reviewRequest.getStars());

        LocalDateTime now = LocalDateTime.now();
        review.setCreatedAt(now);
        review.setUpdatedAt(now);

        Review savedReview = reviewRepository.save(review);

        ReviewResponse response = new ReviewResponse();

        response.setId(savedReview.getId());
        response.setProductId(savedReview.getProductId());
        response.setUserId(savedReview.getUserId());
        response.setComment(savedReview.getComment());
        response.setStars(savedReview.getStars());
        response.setCreatedAt(savedReview.getCreatedAt());
        response.setUpdatedAt(savedReview.getUpdatedAt());

        return response;
    }

    }

