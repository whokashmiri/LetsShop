package com.ecom.dto.review;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;



@Getter
@Setter
public class ReviewRequest {

    private int stars;
    private String productId;
    private String comment;

}
