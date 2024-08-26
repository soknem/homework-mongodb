package com.mongodb.homework.feature.video.dto;

public record VideoRequest(
        String title,

        String fileName,

        Integer orderNo,

        String lectureNo
) {
}
