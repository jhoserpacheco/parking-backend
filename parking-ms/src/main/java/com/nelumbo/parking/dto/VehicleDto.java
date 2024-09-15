package com.nelumbo.parking.dto;

import com.nelumbo.parking.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class VehicleDto {
    private UUID id;
    @NotBlank(message = Constants.ValidationMessages.VEHICLE_PLATE_REQUIRED)
    private String vehiclePlate;
    private int totalVisit;
    @NotBlank(message = Constants.ValidationMessages.MODEL_REQUIRED)
    private String model;
    private UUID idParking;
}
