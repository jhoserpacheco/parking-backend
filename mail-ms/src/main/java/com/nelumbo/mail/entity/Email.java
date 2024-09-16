package com.nelumbo.mail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "emails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "from_email")
    private String emailFrom;

    @Column(name = "to_email")
    private String emailTo;
    private String subject;

    @Column(name = "body")
    private String text;
    private String status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
