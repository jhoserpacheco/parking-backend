package com.nelumbo.parking.controller;

import com.nelumbo.parking.dto.ParkingDto;
import com.nelumbo.parking.projection.ParkingProjection;
import com.nelumbo.parking.service.impl.ParkingServiceImpl;
import com.nelumbo.parking.service.impl.VehicleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Tag(name = "Parking", description = "Endpoints for managing parking lots and vehicles")
public class ParkingController {

    private final ParkingServiceImpl parkingService;
    private final VehicleServiceImpl vehicleService;

    @Operation(summary = "Create parking", description = "Creates a new parking lot. Requires ADMIN role.", tags = { "Parking" })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingDto> createParking(@Valid @RequestBody ParkingDto parkingDto, @RequestHeader("Authorization") String token) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.save(parkingDto, token));
    }

    @Operation(summary = "Get parking by ID", description = "Retrieves the parking lot details by its ID. Requires ADMIN role.", tags = { "Parking" })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/{idParking}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingDto> getParking(@PathVariable UUID idParking) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findById(idParking).get());
    }

    @Operation(summary = "Update parking", description = "Updates an existing parking lot by ID. Requires ADMIN role.", tags = { "Parking" })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{idParking}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingDto> updateParking(@PathVariable UUID idParking ,@Valid @RequestBody ParkingDto parkingDto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.update(idParking, parkingDto, token));
    }

    @Operation(summary = "Delete parking", description = "Deletes a parking lot by ID. Requires ADMIN role.", tags = { "Parking" })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{idParking}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParking(@PathVariable UUID idParking) {
        parkingService.delete(idParking);
    }

    @Operation(summary = "Get all parking lots", description = "Retrieves a list of all parking lots. Requires ADMIN role.", tags = { "Parking" })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParkingDto>> getAllParking() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findAll());
    }

    @Operation(summary = "Get parking lots by socio", description = "Retrieves a list of parking lots for a specific socio by email. Requires ADMIN role.", tags = { "Parking" })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/socio/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParkingDto>> getAllParkingBySocio(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.findAllBySocio(email));
    }

    @Operation(summary = "Get parking detail with vehicles", description = "Retrieves the details of active vehicles in a parking lot. Requires ADMIN or SOCIO role.", tags = { "Parking" })
    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    @GetMapping(path = "/{idParking}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDetailVehicleParking(@PathVariable UUID idParking) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findAllVehicleActiveParking(idParking));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    @GetMapping(path = "/projection", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ParkingProjection> getAllParkingProjection(Pageable pageable) {
        return parkingService.getParkingProjections(pageable);
    }

}