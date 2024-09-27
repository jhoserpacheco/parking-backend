package com.nelumbo.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;


@Document(value = "emails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    private UUID id;

    @Field(name = "from_email")
    private String emailFrom;

    @Field(name = "to_email")
    private String emailTo;
    private String subject;

    @Field(name = "body")
    private String text;
    private String status;
    @Field(name = "created_at")
    private LocalDateTime createdAt;

}
