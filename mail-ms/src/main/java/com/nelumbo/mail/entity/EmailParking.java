package com.nelumbo.mail.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Document( value = "email_parking")
public class EmailParking {
    @Id
    private ObjectId id;
    @Field(name = "from_email")
    private String emailFrom;
    @Field(name = "to_email")
    private String emailTo;
    private String subject;
    private String text;
    @Field(name = "vehicle_plate")
    private String vehiclePlate;
    @Field(name = "parking_name")
    private String parkingName;
    private String status;
}
