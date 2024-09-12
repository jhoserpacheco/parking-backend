package com.nelumbo.user.service;

import com.nelumbo.user.dto.LoginUserDto;
import com.nelumbo.user.dto.RegisterUserDto;
import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.dto.VerifyUserDto;
import com.nelumbo.user.entity.User;

public interface IAuthService {
    User signup(RegisterUserDto input);
    User authenticate(LoginUserDto input);
    void verifyUser(VerifyUserDto input);
    void resendVerificationCode(String email);
    UserDto validate(String token);
}
