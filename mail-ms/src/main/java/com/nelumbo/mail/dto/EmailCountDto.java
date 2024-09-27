package com.nelumbo.mail.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class EmailCountDto {

    @Field(value = "_id")
    private String email;
    private int count;
}
