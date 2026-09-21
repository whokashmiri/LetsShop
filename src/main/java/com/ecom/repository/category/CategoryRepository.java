package com.ecom.repository.category;

import com.ecom.models.category.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
  List<Category> findByParentId(String parentId);
}
