package com.nelumbo.mail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "email_parking")
public class EmailParking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "from_email")
    private String emailFrom;
    @Column(name = "to_email")
    private String emailTo;
    private String subject;
    private String text;
    @Column(name = "vehicle_plate")
    private String vehiclePlate;
    @Column(name = "parking_name")
    private String parkingName;
    private String status;
}
