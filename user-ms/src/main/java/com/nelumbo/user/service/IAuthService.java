package com.nelumbo.user.service;

import com.nelumbo.user.dto.LoginUserDto;
import com.nelumbo.user.dto.RegisterUserDto;
import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.dto.VerifyUserDto;
import com.nelumbo.user.entity.User;
import org.apache.coyote.BadRequestException;

public interface IAuthService {
    User signup(RegisterUserDto input);
    User authenticate(LoginUserDto input) throws BadRequestException;
    void verifyUser(VerifyUserDto input) throws BadRequestException;
    void resendVerificationCode(String email) throws BadRequestException;
    UserDto validate(String token) throws BadRequestException;
}
