package com.nelumbo.user.dto;

import com.nelumbo.user.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    @Email(message = Constants.ValidationMessages.EMAIL_INVALID)
    @NotBlank(message = Constants.ValidationMessages.EMAIL_REQUIRED)
    private String email;

    @NotBlank(message = Constants.ValidationMessages.PASSWORD_REQUIRED)
    private String password;
}