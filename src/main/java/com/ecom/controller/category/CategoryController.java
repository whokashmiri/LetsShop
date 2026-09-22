package com.ecom.controller.category;

import com.ecom.dto.category.CategoryRequest;
import com.ecom.dto.category.CategoryResponse;
import com.ecom.service.category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @GetMapping("/get-category")
    public ResponseEntity<List<CategoryResponse>> getCategory(@RequestParam String parentId){
      List<CategoryResponse> categoryResponseList =  categoryService.getCategoryByParentId(parentId);
      return ResponseEntity.ok(categoryResponseList);

    }


    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getParentCategory(){
        List<CategoryResponse> categoryResponseList =  categoryService.getCategoryByParentIsNull();
        return ResponseEntity.ok(categoryResponseList);

    }
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
       CategoryResponse categoryResponse =  categoryService.createCategory(categoryRequest);
       return ResponseEntity.ok(categoryResponse);

    }
}
