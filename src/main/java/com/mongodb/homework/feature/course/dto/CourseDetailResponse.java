package com.mongodb.homework.feature.course.dto;

import com.mongodb.homework.feature.section.dto.SectionDetailResponse;

import java.time.LocalDateTime;
import java.util.List;

public record CourseDetailResponse(

        String id,

        String title,

        String slug,

        Double price,

        Double discount,

        Boolean isPaid,

        Boolean isDrafted,

        String thumbnail,

        String instructorName,

        String description,

        String categoryId,

        String content,


        List<SectionDetailResponse> sections,

        LocalDateTime lastModifiedAt,

        Boolean isDeleted

) implements CourseResponse {
}
