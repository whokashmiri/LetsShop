package com.ecom.dto.category;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryRequest {

    private String name;
    private String description;
    private String parentId;
    private boolean active;

}
