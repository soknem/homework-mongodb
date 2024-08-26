package com.mongodb.homework.feature.course;

import com.mongodb.homework.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {

    Optional<Course> findBySlug(String slug);

    Page<Course> findAllByIsDraftedFalse(Pageable pageable);

    Page<Course> findAllByIsDraftedTrue(Pageable pageable);

    Page<Course> findAllByInstructorName(String instructorName, Pageable pageable);

    Page<Course> findAllByPriceLessThanEqual(Double price, Pageable pageable);

    Long countByCategoryId(String categoryId);
}
