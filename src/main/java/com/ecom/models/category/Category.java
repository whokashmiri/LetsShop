package com.ecom.models.category;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("categories")
@Getter
@Setter
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    private String parentId;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
