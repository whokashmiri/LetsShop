package com.ecom.service.category;

import com.ecom.dto.category.CategoryResponse;
import com.ecom.exceptions.product.CategoryNotFoundException;
import com.ecom.models.category.Category;
import com.ecom.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public List<CategoryResponse> getCategoryByParentId(String parentId){
      List<Category> categories =  categoryRepository.findByParentId(parentId);
      if (categories.isEmpty()){
          throw new CategoryNotFoundException("Category Not Found");
      }
       List <CategoryResponse> categoryResponseList = new ArrayList<>();
      for (Category category : categories){
          CategoryResponse categoryResponse = new CategoryResponse();
          categoryResponse.setId(category.getId());
          categoryResponse.setName(category.getName());
          categoryResponse.setDescription(category.getDescription());
          categoryResponse.setActive(category.isActive());

          categoryResponse.setUpdatedAt(category.getUpdatedAt());
          categoryResponse.setCreatedAt(category.getCreatedAt());
          categoryResponseList.add(categoryResponse);

      }
      return categoryResponseList;

    }
}
