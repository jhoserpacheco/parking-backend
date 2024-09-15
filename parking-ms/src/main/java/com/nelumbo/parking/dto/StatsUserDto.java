package com.nelumbo.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatsUserDto {
    private String emailUser;
    private long countVehicles;
}
