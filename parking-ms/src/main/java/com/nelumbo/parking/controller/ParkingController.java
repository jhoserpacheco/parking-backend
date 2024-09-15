package com.nelumbo.parking.controller;

import com.nelumbo.parking.dto.ParkingDto;
import com.nelumbo.parking.service.impl.ParkingServiceImpl;
import com.nelumbo.parking.service.impl.VehicleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingServiceImpl parkingService;
    private final VehicleServiceImpl vehicleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingDto> createParking(@RequestBody ParkingDto parkingDto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.save(parkingDto, token));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/{idParking}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingDto> getParking(@PathVariable UUID idParking) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findById(idParking).get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{idParking}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingDto> updateParking(@PathVariable UUID idParking ,@RequestBody ParkingDto parkingDto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.update(idParking, parkingDto, token));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{idParking}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteParking(@PathVariable UUID idParking) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.delete(idParking));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParkingDto>> getAllParking() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/socio/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParkingDto>> getAllParkingBySocio(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findAllBySocio(email));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    @GetMapping(path = "/{idParking}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDetailVehicleParking(@PathVariable UUID idParking) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findAllVehicleActiveParking(idParking));
    }

}