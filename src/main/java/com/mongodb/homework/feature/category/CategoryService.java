package com.mongodb.homework.feature.category;

import com.mongodb.homework.feature.category.dto.CategoryPopularResponse;
import com.mongodb.homework.feature.category.dto.CategoryRequest;
import com.mongodb.homework.feature.category.dto.CategoryResponse;
import com.mongodb.homework.feature.category.dto.CategoryUpdateRequest;
import java.util.List;

public interface CategoryService {

    void createCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(String id);

    CategoryResponse updateCategoryById(String id, CategoryUpdateRequest categoryUpdateRequest);

    void disableCategoryById(String id);

    void enableCategoryById(String id);

    void deleteCategoryById(String id);

    List<CategoryPopularResponse> getPopularCategories();
}
