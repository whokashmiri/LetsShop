package com.ecom.controller.review;

import com.ecom.dto.review.ReviewRequest;
import com.ecom.dto.review.ReviewResponse;
import com.ecom.service.review.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsOfProduct(
            @PathVariable String productId) {

        List<ReviewResponse> reviews =
                reviewService.getReviewsOfProduct(productId);

        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<ReviewResponse> createReviewOfProduct(
            @PathVariable String productId,
            @RequestBody ReviewRequest reviewRequest) {

        ReviewResponse reviewResponse =
                reviewService.createReviewOfProduct(
                        productId,
                        reviewRequest
                );

        return ResponseEntity.ok(reviewResponse);
    }
}