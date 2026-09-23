package com.ecom.models.review;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document("reviews")
public class Review {
    @Id
    private String id;
    private int stars;
    private String userId;
    private String productId;
    private String comment;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
