package com.nelumbo.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class StatsParkingDto {
    private UUID parkingId;
    private String parkingName;
    private String emailUser;
    private double totalEarning;
}
