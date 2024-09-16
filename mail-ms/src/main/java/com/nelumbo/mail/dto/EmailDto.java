package com.nelumbo.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmailDto {
    private UUID id;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;
    private StatusEmailEnum status;
    private LocalDateTime createdAt;
}
