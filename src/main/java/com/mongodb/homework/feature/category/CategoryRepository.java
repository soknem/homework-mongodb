package com.mongodb.homework.feature.category;

import com.mongodb.homework.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
