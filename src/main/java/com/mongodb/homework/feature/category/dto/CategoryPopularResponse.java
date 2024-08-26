package com.mongodb.homework.feature.category.dto;

import lombok.Builder;

@Builder
public record CategoryPopularResponse(

        String icon,

        String name,

        Long totalCourse
) {
}
