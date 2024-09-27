package com.nelumbo.user.controller;

import com.nelumbo.user.dto.ChangeRolDto;
import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.projection.UserProjection;
import com.nelumbo.user.service.impl.UserServiceImpl;
import com.nelumbo.user.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ADMIN') or hasAuthority('ADMIN')")
    @PutMapping(value = "/change-rol", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeRol(@RequestBody @Valid ChangeRolDto changeRolDto) throws BadRequestException {
        userService.changeRol(changeRolDto);
        return ResponseEntity.ok(Constants.Message.USER_CHANGE_ROL_SUCCESS);
    }

    @Operation(summary = "Get user by email", description = "Retrieves the user information by email.", tags = { "User Management" })
    @GetMapping(path = "/by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> getByEmail(@RequestParam String email) {
        UserDto user = userService.findByEmailDto(email);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users", description = "Retrieves all users or filters by role ID if provided.", tags = { "User Management" })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Page<UserProjection>> getAll(@RequestParam(required = false) Integer idRol, Pageable pageable) {
        Page<UserProjection> users;
        users = userService.findAll(pageable);
        /*if (idRol != null) {
            users = userService.findAllbyRol(idRol, pageable);
        } else {
            users = userService.findAll(pageable);
        }*/
        return ResponseEntity.ok(users);
    }

}
