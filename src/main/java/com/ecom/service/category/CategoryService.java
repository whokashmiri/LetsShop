package com.ecom.service.category;

import com.ecom.models.category.Category;
import com.ecom.repository.category.CategoryRepository;

import java.util.List;

public class CategoryService {
    private CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public List<Category> getCategoryByParentId(String parentId){
      List<Category>  categoryList=  categoryRepository.findByParentId(parentId);
      return  categoryList;
    }
}
