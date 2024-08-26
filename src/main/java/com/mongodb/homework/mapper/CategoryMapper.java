package com.mongodb.homework.mapper;

import com.mongodb.homework.domain.Category;
import com.mongodb.homework.feature.category.dto.CategoryRequest;
import com.mongodb.homework.feature.category.dto.CategoryResponse;
import com.mongodb.homework.feature.category.dto.CategoryUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category fromCategoryRequest(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromRequest(@MappingTarget Category category, CategoryUpdateRequest categoryUpdateRequest);

}
