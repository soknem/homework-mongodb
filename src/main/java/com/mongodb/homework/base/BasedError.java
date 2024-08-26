package com.mongodb.homework.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedError<T> {

    private String code;

    // Detail error for user
    private T description;

}
