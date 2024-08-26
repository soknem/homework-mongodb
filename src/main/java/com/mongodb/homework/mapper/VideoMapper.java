package com.mongodb.homework.mapper;

import com.mongodb.homework.domain.Video;
import com.mongodb.homework.feature.video.dto.VideoRequest;
import com.mongodb.homework.feature.video.dto.VideoResponse;
import com.mongodb.homework.feature.video.dto.VideoUpdateRequest;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    Video fromRequest(VideoRequest videoRequest);

    VideoResponse toResponse(Video video);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVideoFromRequest(@MappingTarget Video Video, VideoUpdateRequest videoUpdateRequest);

}
