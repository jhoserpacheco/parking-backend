package com.nelumbo.mail.controller;

import com.nelumbo.mail.dto.EmailParkingDto;
import com.nelumbo.mail.dto.ResponseEmailDto;
import com.nelumbo.mail.service.impl.EmailParkingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailParkingController {

    private final EmailParkingServiceImpl emailParkingService;

    @PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEmailDto emailParking(@RequestBody EmailParkingDto emailParkingDto) {
        return emailParkingService.sendEmail(emailParkingDto);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEmailDto emailParking(@PathVariable UUID id) {
        return emailParkingService.getStatusEmail(id);
    }
}
