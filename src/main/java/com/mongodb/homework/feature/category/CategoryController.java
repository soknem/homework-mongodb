package com.mongodb.homework.feature.category;

import com.mongodb.homework.feature.category.dto.CategoryPopularResponse;
import com.mongodb.homework.feature.category.dto.CategoryRequest;
import com.mongodb.homework.feature.category.dto.CategoryResponse;
import com.mongodb.homework.feature.category.dto.CategoryUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        categoryService.createCategory(categoryRequest);

    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {

        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable String id) {

        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategoryById(@PathVariable String id,
                                               @Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return categoryService.updateCategoryById(id, categoryUpdateRequest);
    }

    @PutMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableCategoryById(@PathVariable String id) {

        categoryService.disableCategoryById(id);
    }

    @PutMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableCategoryById(@PathVariable String id) {

        categoryService.enableCategoryById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable String id) {

        categoryService.deleteCategoryById(id);
    }


    @GetMapping("/popular")
    public List<CategoryPopularResponse> getAllPopularCategories() {

        return categoryService.getPopularCategories();
    }


}
