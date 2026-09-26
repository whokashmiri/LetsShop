package com.ecom.dto.review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponse {
    private String id;

    @Min(1)
    @Max(5)
    private int stars;

    private String userId;
    private String productId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
