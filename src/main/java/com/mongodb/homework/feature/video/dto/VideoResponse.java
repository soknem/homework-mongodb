package com.mongodb.homework.feature.video.dto;

public record VideoResponse(

        String title,

        String fileName,

        Integer orderNo,

        String lectureNo
) {
}
