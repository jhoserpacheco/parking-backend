package com.nelumbo.parking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health", description = "Health check endpoints")
@RequestMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthController {

    @Operation(summary = "Health check", description = "Checks if the service is up and running.", tags = { "Health" })
    @GetMapping
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service is up and running");
    }
}