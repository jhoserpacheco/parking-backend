package com.nelumbo.mail.dto;

import com.nelumbo.mail.util.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class EmailParkingDto {
    @NotBlank(message = Constants.ValidationMessages.EMAIL_REQUIRED)
    @Email(message = Constants.ValidationMessages.EMAIL_INVALID)
    private String emailTo;
    @NotBlank(message = Constants.ValidationMessages.SUBJECT_REQUIRED)
    private String subject;
    @NotBlank(message = Constants.ValidationMessages.TEXT_REQUIRED)
    private String text;
    @NotBlank(message = Constants.ValidationMessages.VEHICLE_PLATE_REQUIRED)
    private String vehiclePlate;
    @NotBlank(message = Constants.ValidationMessages.PARKING_NAME_REQUIRED)
    private String parkingName;
    private String status;
}