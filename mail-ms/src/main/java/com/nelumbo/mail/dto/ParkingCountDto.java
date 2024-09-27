package com.nelumbo.mail.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class ParkingCountDto {
    @Field(value = "_id")
    private String parkingName;
    private int count;
}

