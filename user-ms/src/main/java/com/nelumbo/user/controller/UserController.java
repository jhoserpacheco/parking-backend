package com.nelumbo.user.controller;

import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.service.impl.UserServiceImpl;
import com.nelumbo.user.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@Tag(name = "User Management", description = "Endpoints for user management")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "Change user role", description = "Changes the role of the user identified by email.", tags = { "User Management" })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/changeRol", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeRol(@RequestParam String email, @RequestParam int idRol) {
        userService.changeRol(email, idRol);
        return ResponseEntity.ok(Constants.Message.USER_CHANGE_ROL_SUCCESS);
    }

    @Operation(summary = "Get all users by role", description = "Retrieves all users with the specified role ID.", tags = { "User Management" })
    @GetMapping(value = "/getAllByRol", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllByRol(@RequestParam int idRol) {
        List<UserDto> users = userService.findAllbyRol(idRol);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by email", description = "Retrieves the user information by email.", tags = { "User Management" })
    @GetMapping(value = "/getByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getByEmail(@RequestParam String email) {
        UserDto user = userService.findByEmailDto(email);
        return ResponseEntity.ok(user);
    }

}
