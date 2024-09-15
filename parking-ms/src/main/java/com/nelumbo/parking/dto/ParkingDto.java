package com.nelumbo.parking.dto;

import com.nelumbo.parking.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class ParkingDto {

    private UUID id;
    @NotBlank(message = Constants.ValidationMessages.NAME_REQUIRED)
    private String name;
    @NotNull(message = Constants.ValidationMessages.MAX_CAPACITY_REQUIRED)
    @Positive(message = Constants.ValidationMessages.MAX_CAPACITY_POSITIVE)
    private int maxCapacity;

    private int currentCapacity;

    @NotNull(message = Constants.ValidationMessages.COST_HOUR_REQUIRED)
    @Positive(message = Constants.ValidationMessages.COST_HOUR_POSITIVE)
    private double costHour;

    @NotBlank(message = Constants.ValidationMessages.DIRECTION_REQUIRED)
    private String direction;

    @NotBlank(message = Constants.ValidationMessages.EMAIL_REQUIRED)
    @Email(message = Constants.ValidationMessages.EMAIL_FORMAT_INVALID)
    private String emailUser;

}
