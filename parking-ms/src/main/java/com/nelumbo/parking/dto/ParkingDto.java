package com.nelumbo.parking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class ParkingDto {

    private UUID id;
    private String name;
    private int maxCapacity;
    private int currentCapacity;
    private double costHour;
    private String direction;
    private String emailUser;

}
