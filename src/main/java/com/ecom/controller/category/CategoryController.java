package com.ecom.controller.category;

import com.ecom.dto.category.CategoryResponse;
import com.ecom.service.category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @GetMapping("/get-category")
    public ResponseEntity<List<CategoryResponse>> getCategory(@RequestParam String parentId){
      List<CategoryResponse> categoryResponseList =  categoryService.getCategoryByParentId(parentId);
      return ResponseEntity.ok(categoryResponseList);

    }
}
