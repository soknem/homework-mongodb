package com.mongodb.homework.feature.category.dto;

public record CategoryUpdateRequest(
        String title,

        String icon,

        String description
) {
}
