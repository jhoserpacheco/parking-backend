package com.nelumbo.mail.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class EmailParkingDto {
    private String emailTo;
    private String subject;
    private String text;
    private String vehiclePlate;
    private String parkingName;
    private String status;
}