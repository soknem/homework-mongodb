package com.mongodb.homework.feature.section.dto;

import com.mongodb.homework.domain.Video;

import java.util.List;

public record SectionUpdateRequest(

        String title,

        Integer orderNo,

        List<Video> videos
) {
}
