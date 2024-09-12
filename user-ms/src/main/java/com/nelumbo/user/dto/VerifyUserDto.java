package com.nelumbo.user.dto;

import com.nelumbo.user.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDto {
    @Email(message = Constants.ValidationMessages.EMAIL_INVALID)
    @NotBlank(message = Constants.ValidationMessages.EMAIL_REQUIRED)
    private String email;

    @Pattern(regexp = "\\d{6}", message = Constants.ValidationMessages.VERIFICATION_CODE_SIZE)
    private String verificationCode;
}