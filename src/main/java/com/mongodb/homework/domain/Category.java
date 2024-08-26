package com.mongodb.homework.domain;

import com.mongodb.homework.config.Auditable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "categories")
@Data
public class Category extends Auditable {
    @Id
    private String id;

    private String title;

    private String icon;

    private String description;

    private Boolean isDeleted;

}
