package com.nelumbo.parking.controller;

import com.nelumbo.parking.dto.RegisterParkingDto;
import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.service.impl.ParkingServiceHistoryImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/parking/history")
@RequiredArgsConstructor
@Tag(name = "Parking History", description = "Endpoints for managing parking entries and exits history")
public class ParkingHistoryController {

    private final ParkingServiceHistoryImpl parkingServiceHistory;

    @Operation(summary = "Register vehicle entry", description = "Registers a vehicle's entry in the parking lot. Requires SOCIO role.", tags = { "Parking History" })
    @PreAuthorize("hasRole('SOCIO')")
    @PostMapping(path = "/register-entry/{parkingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterParkingDto> registerEntry(@RequestBody VehicleDto vehicle, @PathVariable UUID parkingId) {
        return ResponseEntity.ok(parkingServiceHistory.registerEntry(vehicle, parkingId));
    }

    @Operation(summary = "Register vehicle exit", description = "Registers a vehicle's exit from the parking lot. Requires SOCIO role.", tags = { "Parking History" })
    @PreAuthorize("hasRole('SOCIO')")
    @PostMapping(path = "/register-exit/{parkingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterParkingDto> registerExit(@RequestBody VehicleDto vehicle, @PathVariable UUID parkingId) {
        return ResponseEntity.ok(parkingServiceHistory.registerExit(vehicle, parkingId));
    }


}
