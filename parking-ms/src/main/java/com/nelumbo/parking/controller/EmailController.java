package com.nelumbo.parking.controller;

import com.nelumbo.parking.feign.EmailClient;
import com.nelumbo.parking.feign.EmailClientImpl;
import com.nelumbo.parking.feign.EmailParkingDto;
import com.nelumbo.parking.feign.ResponseEmailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@Tag(name = "Email", description = "Endpoints for managing and sending parking-related emails")
public class EmailController {

    private final EmailClientImpl emailService;
    private final EmailClient emailClient;

    @Operation(summary = "Send email", description = "Sends a parking-related email if the vehicle exists. Requires ADMIN role.", tags = { "Email" })
    @PostMapping(path = "/send")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseEmailDto> email(@RequestHeader("Authorization") String token, @RequestBody EmailParkingDto emailParkingDto) {
        return ResponseEntity.ok(emailService.sendEmailIfVehicleExists(token, emailParkingDto));
    }

    @Operation(summary = "Get email by ID", description = "Retrieves the details of a sent email by its ID. Requires ADMIN role.", tags = { "Email" })
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseEmailDto> email(@RequestHeader("Authorization") String token, @PathVariable UUID id) {
        return ResponseEntity.ok(emailClient.getEmailById(token, id));
    }

}
