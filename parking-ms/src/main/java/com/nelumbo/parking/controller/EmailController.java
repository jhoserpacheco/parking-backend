package com.nelumbo.parking.controller;

import com.nelumbo.parking.feign.EmailClient;
import com.nelumbo.parking.feign.EmailClientImpl;
import com.nelumbo.parking.feign.EmailParkingDto;
import com.nelumbo.parking.feign.ResponseEmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailClientImpl emailService;
    private final EmailClient emailClient;

    @PostMapping(path = "/send")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseEmailDto> email(@RequestHeader("Authorization") String token, @RequestBody EmailParkingDto emailParkingDto) {
        return ResponseEntity.ok(emailService.sendEmailIfVehicleExists(token, emailParkingDto));
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseEmailDto> email(@RequestHeader("Authorization") String token, @PathVariable UUID id) {
        return ResponseEntity.ok(emailClient.getEmailById(token, id));
    }

}
