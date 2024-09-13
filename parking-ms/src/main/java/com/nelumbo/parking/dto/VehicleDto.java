package com.nelumbo.parking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class VehicleDto {
    private UUID id;
    private String vehiclePlate;
    private int totalVisit;
    private String model;
    private ParkingDto parking;
}
