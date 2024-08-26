package com.mongodb.homework.feature.category;

import com.mongodb.homework.domain.Category;
import com.mongodb.homework.feature.category.dto.CategoryPopularResponse;
import com.mongodb.homework.feature.category.dto.CategoryRequest;
import com.mongodb.homework.feature.category.dto.CategoryResponse;
import com.mongodb.homework.feature.category.dto.CategoryUpdateRequest;
import com.mongodb.homework.feature.course.CourseRepository;
import com.mongodb.homework.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final MongoTemplate mongoTemplate;

    private final CourseRepository courseRepository;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {

        Category category = categoryMapper.fromCategoryRequest(categoryRequest);

        category.setIsDeleted(false);

        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(String id) {

        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found", id)));

        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategoryById(String id, CategoryUpdateRequest categoryUpdateRequest) {

        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found", id)));

        categoryMapper.updateCategoryFromRequest(category, categoryUpdateRequest);

        categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Override
    public void disableCategoryById(String id) {

        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found", id)));

        category.setIsDeleted(true);

        categoryRepository.save(category);
    }

    @Override
    public void enableCategoryById(String id) {

        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found", id)));

        category.setIsDeleted(false);

        categoryRepository.save(category);

    }

    @Override
    public void deleteCategoryById(String id) {

        Category category =
                categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("category = %s has not been found", id)));

        categoryRepository.delete(category);

    }

    @Override
    public List<CategoryPopularResponse> getPopularCategories() {

        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(category -> {

                    long totalCourse = courseRepository.countByCategoryId(category.getId());

                    return CategoryPopularResponse.builder()
                            .name(category.getTitle())
                            .icon(category.getIcon())
                            .totalCourse(totalCourse)
                            .build();
                })
                .sorted((category1, category2) -> category2.totalCourse().compareTo(category1.totalCourse()))
                .toList();
    }
}
