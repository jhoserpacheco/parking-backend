package com.nelumbo.user.dto;

import com.nelumbo.user.utils.Constants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeRolDto {

    @Email(message = Constants.ValidationMessages.EMAIL_INVALID)
    @NotBlank(message = Constants.ValidationMessages.EMAIL_REQUIRED)
    private String email;

    @NotNull(message = Constants.ValidationMessages.ID_ROL_REQUIRED)
    @Positive(message = Constants.ValidationMessages.NUMBER_NEGATIVE)
    private Integer idRol;
}
