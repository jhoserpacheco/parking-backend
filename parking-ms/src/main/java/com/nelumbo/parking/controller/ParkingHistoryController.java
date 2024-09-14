package com.nelumbo.parking.controller;

import com.nelumbo.parking.dto.RegisterParkingDto;
import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.service.impl.ParkingServiceHistoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/parking/history")
@RequiredArgsConstructor
public class ParkingHistoryController {

    private final ParkingServiceHistoryImpl parkingServiceHistory;

    @PreAuthorize("hasRole('SOCIO')")
    @PostMapping(path = "/register-entry/{parkingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterParkingDto> registerEntry(@RequestBody VehicleDto vehicle, @PathVariable UUID parkingId) {
        return ResponseEntity.ok(parkingServiceHistory.registerEntry(vehicle, parkingId));
    }

    @PreAuthorize("hasRole('SOCIO')")
    @PostMapping(path = "/register-exit/{parkingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterParkingDto> registerExit(@RequestBody VehicleDto vehicle, @PathVariable UUID parkingId) {
        return ResponseEntity.ok(parkingServiceHistory.registerExit(vehicle, parkingId));
    }


}
