package com.nelumbo.parking.feign;

import com.nelumbo.parking.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EmailParkingDto {

    @NotBlank(message = Constants.ValidationMessages.EMAIL_REQUIRED)
    @Email(message = Constants.ValidationMessages.EMAIL_FORMAT_INVALID)
    private String emailTo;

    @NotBlank(message = Constants.ValidationMessages.EMAIL_SUBJECT_REQUIRED)
    private String subject;

    @NotBlank(message = Constants.ValidationMessages.EMAIL_BODY_REQUIRED)
    private String text;

    @NotBlank(message = Constants.ValidationMessages.VEHICLE_PLATE_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = Constants.ValidationMessages.VEHICLE_REGEX_VALIDATION)
    private String vehiclePlate;

    @NotBlank(message = Constants.ValidationMessages.NAME_REQUIRED)
    private String parkingName;
}
