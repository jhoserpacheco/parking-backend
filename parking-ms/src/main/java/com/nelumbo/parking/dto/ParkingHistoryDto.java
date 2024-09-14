package com.nelumbo.parking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class ParkingHistoryDto {

    private UUID id;

    private UUID idParking;

    private String vehiclePlate;

    private double totalCost;

    private LocalDateTime entryDate;

    private LocalDateTime exitDate;
}
