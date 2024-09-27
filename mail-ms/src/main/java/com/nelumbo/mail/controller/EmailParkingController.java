package com.nelumbo.mail.controller;

import com.nelumbo.mail.dto.EmailParkingDto;
import com.nelumbo.mail.dto.ResponseEmailDto;
import com.nelumbo.mail.entity.EmailParking;
import com.nelumbo.mail.service.impl.EmailParkingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Tag(name = "Email", description = "Endpoints for sending and tracking parking-related emails")
public class EmailParkingController {

    private final EmailParkingServiceImpl emailParkingService;

    @Operation(summary = "Send parking email", description = "Sends an email with parking information based on the provided details.", tags = { "Email" })
    @PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEmailDto emailParking(@Valid @RequestBody EmailParkingDto emailParkingDto) {
        return emailParkingService.sendEmail(emailParkingDto);
    }

    @Operation(summary = "Get email status", description = "Retrieves the status of a previously sent email by its UUID.", tags = { "Email" })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEmailDto emailParking(@PathVariable ObjectId id) {
        return emailParkingService.getStatusEmail(id);
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmailParking>> searchQuery(@RequestParam String query){
        return ResponseEntity.ok(emailParkingService.search(query));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmailParking>> findAll(@RequestParam(required = false) String status){
        return ResponseEntity.ok(emailParkingService.findAll(status));
    }
}
