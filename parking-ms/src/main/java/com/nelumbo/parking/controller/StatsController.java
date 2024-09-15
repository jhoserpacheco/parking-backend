package com.nelumbo.parking.controller;

import com.nelumbo.parking.dto.StatsParkingDto;
import com.nelumbo.parking.dto.StatsUserDto;
import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.service.impl.StatsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsServiceImpl statsService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    @GetMapping(path = "/top10-most-registered-vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDto>> top10MostRegisteredVehicles() {
        return ResponseEntity.ok(statsService.Top10MostRegisteredVehicles());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    @GetMapping(path = "/top10-most-registered-vehicle-by-parkingId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDto>> top10MostRegisteredVehiclesByParkingId(@RequestParam UUID parkingId) {
        return ResponseEntity.ok(statsService.Top10MostRegisteredByParkingId(parkingId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    @GetMapping(path = "/get-vehicles-registered-first-time", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDto>> vehiclesRegisteredForTheFirstTime() {
        return ResponseEntity.ok(statsService.getAllVehiclesRegisteredForTheFirstTime());
    }

    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping(path = "/top3-socio-most-vehicle-registered-this-week", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatsUserDto> getTop3UsersWithMostVehiclesThisWeek() {
        return statsService.top3SociosMostRegisteredCurrentWeek();
    }

    @GetMapping(path = "/top3-parking-most-earning", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatsParkingDto> getTop3ParkingsWithHighestEarningsThisWeek() {
        return statsService.top3ParkingsWithHighestEarningsThisWeek();
    }

    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/earnings/day/{parkingId}")
    public ResponseEntity<Double> getEarningsForDay(@PathVariable UUID parkingId) {
        double earnings = statsService.getEarningsForToday(parkingId);
        return ResponseEntity.ok(earnings);
    }

    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/earnings/week/{parkingId}")
    public ResponseEntity<Double> getEarningsForWeek(@PathVariable UUID parkingId) {
        double earnings = statsService.getEarningsForWeek(parkingId);
        return ResponseEntity.ok(earnings);
    }

    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/earnings/month/{parkingId}")
    public ResponseEntity<Double> getEarningsForMonth(@PathVariable UUID parkingId) {
        double earnings = statsService.getEarningsForMonth(parkingId);
        return ResponseEntity.ok(earnings);
    }

    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/earnings/year/{parkingId}")
    public ResponseEntity<Double> getEarningsForYear(@PathVariable UUID parkingId) {
        double earnings = statsService.getEarningsForYear(parkingId);
        return ResponseEntity.ok(earnings);
    }
}
