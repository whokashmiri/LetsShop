package com.ecom.dto.review;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponse {
    private String id;
    private int stars;
    private String userId;
    private String productId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
