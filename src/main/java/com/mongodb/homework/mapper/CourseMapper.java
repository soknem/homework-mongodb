package com.mongodb.homework.mapper;

import com.mongodb.homework.domain.Course;
import com.mongodb.homework.feature.course.dto.CourseDetailResponse;
import com.mongodb.homework.feature.course.dto.CourseRequest;
import com.mongodb.homework.feature.course.dto.CourseSnippetResponse;
import com.mongodb.homework.feature.course.dto.CourseUpdateRequest;
import com.mongodb.homework.feature.section.dto.SectionDetailResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course fromRequest(CourseRequest courseRequest);

    CourseSnippetResponse toCourseSnippetResponse(Course course);

    CourseDetailResponse toCourseDetailResponse(Course course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourseFromRequest(@MappingTarget Course course, CourseUpdateRequest courseUpdateRequest);

}
