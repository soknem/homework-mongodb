package com.mongodb.homework.domain;

import com.mongodb.homework.config.Auditable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class Section {

    private String title;

    private Integer orderNo;

    private String courseId;

    private List<Video> videos;


}
