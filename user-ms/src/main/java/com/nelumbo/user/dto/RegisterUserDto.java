package com.nelumbo.user.dto;

import com.nelumbo.user.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    @Email(message = Constants.ValidationMessages.EMAIL_INVALID)
    @NotBlank(message = Constants.ValidationMessages.EMAIL_REQUIRED)
    private String email;

    @Size(min = 8, message = Constants.ValidationMessages.PASSWORD_SIZE)
    @NotBlank(message = Constants.ValidationMessages.PASSWORD_REQUIRED)
    private String password;

    @NotBlank(message = Constants.ValidationMessages.FULL_NAME_REQUIRED)
    private String fullName;
}