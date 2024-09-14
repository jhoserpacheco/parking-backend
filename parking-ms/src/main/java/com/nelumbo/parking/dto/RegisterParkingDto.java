package com.nelumbo.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RegisterParkingDto {
    private UUID parkingId;
    private String message;
    private String vehiclePlate;
    private LocalDateTime parkingTime;
}
