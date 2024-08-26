package com.mongodb.homework.mapper;

import com.mongodb.homework.domain.Section;
import com.mongodb.homework.feature.section.dto.SectionDetailResponse;
import com.mongodb.homework.feature.section.dto.SectionRequest;
import com.mongodb.homework.feature.section.dto.SectionResponse;
import com.mongodb.homework.feature.section.dto.SectionUpdateRequest;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    Section fromRequest(SectionRequest sectionRequest);

    SectionResponse toResponse(Section section);

    SectionDetailResponse toSectionDetailResponse(Section section);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSectionFromRequest(@MappingTarget Section section, SectionUpdateRequest sectionUpdateRequest);

}
