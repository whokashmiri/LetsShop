package com.ecom.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Getter
@Setter
public class CategoryResponse {
    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String parentId;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
