package com.mongodb.homework.domain;

import com.mongodb.homework.config.Auditable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "courses")
public class Course extends Auditable {

    @Id
    private String id;

    private String title;

    private String slug;

    private Double price;

    private Double discount;

    private Boolean isPaid;

    private Boolean isDrafted;

    private String thumbnail;

    private String instructorName;

    private String description;

    private String content;

    private Boolean isDeleted;

    private String categoryId;

    private List<Section> sections;

}
