package com.mongodb.homework.feature.video.dto;

public record VideoUpdateRequest(
        String title,

        String fileName,

        Integer orderNo,

        String lectureNo
) {
}
