package com.mongodb.homework.feature.course.dto;

public record CourseRequest(

        String title,

        String slug,

        Double price,

        Double discount,

        Boolean isPaid,

        String thumbnail,

        String instructorName,

        String description,

        String categoryId,

        String content
) {
}
