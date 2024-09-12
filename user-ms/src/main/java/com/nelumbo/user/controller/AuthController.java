package com.nelumbo.user.controller;

import com.nelumbo.user.dto.*;
import com.nelumbo.user.entity.User;
import com.nelumbo.user.service.impl.AuthServiceImpl;
import com.nelumbo.user.service.impl.JwtServiceImpl;
import com.nelumbo.user.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints related to user authentication and registration")
public class AuthController {

    private final JwtServiceImpl jwtServiceImpl;
    private final AuthServiceImpl authenticationService;

    @Operation(summary = "Register a new user", description = "Registers a new user with the provided registration details.", tags = { "Authentication" })
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Authenticate user", description = "Authenticates the user and returns a JWT token.", tags = { "Authentication" })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto){
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtServiceImpl.generateToken(authenticatedUser, authenticatedUser.getRol().getName());
        LoginResponseDto loginResponse = new LoginResponseDto(jwtToken, jwtServiceImpl.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "Verify user account", description = "Verifies the user account using the provided verification details.", tags = { "Authentication" })
    @PostMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyUser(@RequestBody VerifyUserDto verifyUserDto)  {
        authenticationService.verifyUser(verifyUserDto);
        return ResponseEntity.ok(Constants.Message.ACCOUNT_VERIFIED);
    }

    @Operation(summary = "Resend verification code", description = "Resends the verification code to the provided email address.", tags = { "Authentication" })
    @PostMapping(value ="/resend", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resendVerificationCode(@RequestParam String email)  {
        authenticationService.resendVerificationCode(email);
        return ResponseEntity.ok(Constants.Message.RESEND_CODE);
    }

    @Operation(summary = "Validate JWT token", description = "Validates the JWT token and returns user details.", tags = { "Authentication" })
    @GetMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> validateToken(@RequestHeader("Authorization") String token)  {
        UserDto user = authenticationService.validate(token);
        return ResponseEntity.ok(user);
    }

}