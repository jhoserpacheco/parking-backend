package com.nelumbo.parking.controller;

import com.nelumbo.parking.dto.StatsParkingDto;
import com.nelumbo.parking.dto.StatsUserDto;
import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.service.impl.StatsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Statistics", description = "Endpoints for retrieving statistics related to vehicles and parking lots")
public class StatsController {

    private final StatsServiceImpl statsService;

    @Operation(summary = "Get top 10 most registered vehicles", description = "Returns a list of the top 10 most registered vehicles across all parking lots.")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    @GetMapping(path = "/top10-most-registered-vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDto>> top10MostRegisteredVehicles() {
        return ResponseEntity.ok(statsService.Top10MostRegisteredVehicles());
    }

    @Operation(summary = "Get top 10 most registered vehicles by parking ID", description = "Returns a list of the top 10 most registered vehicles for a specific parking lot.")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    @GetMapping(path = "/top10-most-registered-vehicle-by-parkingId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDto>> top10MostRegisteredVehiclesByParkingId(@RequestParam UUID parkingId) {
        return ResponseEntity.ok(statsService.Top10MostRegisteredByParkingId(parkingId));
    }

    @Operation(summary = "Get vehicles registered for the first time", description = "Returns a list of vehicles that have been registered for the first time.")
    @PreAuthorize("hasAnyRole('ADMIN', 'SOCIO')")
    @GetMapping(path = "/get-vehicles-registered-first-time", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDto>> vehiclesRegisteredForTheFirstTime() {
        return ResponseEntity.ok(statsService.getAllVehiclesRegisteredForTheFirstTime());
    }

    @Operation(summary = "Get top 3 SOCIO users with most vehicle registrations this week", description = "Returns a list of the top 3 SOCIO users who registered the most vehicles during the current week.")
    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping(path = "/top3-socio-most-vehicle-registered-this-week", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatsUserDto> getTop3UsersWithMostVehiclesThisWeek() {
        return statsService.top3SociosMostRegisteredCurrentWeek();
    }

    @Operation(summary = "Get top 3 parking lots with highest earnings this week", description = "Returns a list of the top 3 parking lots with the highest earnings during the current week.")
    @GetMapping(path = "/top3-parking-most-earning", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StatsParkingDto> getTop3ParkingsWithHighestEarningsThisWeek() {
        return statsService.top3ParkingsWithHighestEarningsThisWeek();
    }

    @Operation(summary = "Get daily earnings for a parking lot", description = "Returns the earnings for a specific parking lot on the current day.")
    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/earnings/day/{parkingId}")
    public ResponseEntity<Double> getEarningsForDay(@PathVariable UUID parkingId) {
        double earnings = statsService.getEarningsForToday(parkingId);
        return ResponseEntity.ok(earnings);
    }

    @Operation(summary = "Get weekly earnings for a parking lot", description = "Returns the earnings for a specific parking lot during the current week.")
    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/earnings/week/{parkingId}")
    public ResponseEntity<Double> getEarningsForWeek(@PathVariable UUID parkingId) {
        double earnings = statsService.getEarningsForWeek(parkingId);
        return ResponseEntity.ok(earnings);
    }

    @Operation(summary = "Get monthly earnings for a parking lot", description = "Returns the earnings for a specific parking lot during the current month.")
    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/earnings/month/{parkingId}")
    public ResponseEntity<Double> getEarningsForMonth(@PathVariable UUID parkingId) {
        double earnings = statsService.getEarningsForMonth(parkingId);
        return ResponseEntity.ok(earnings);
    }

    @Operation(summary = "Get yearly earnings for a parking lot", description = "Returns the earnings for a specific parking lot during the current year.")
    @PreAuthorize("hasRole('SOCIO')")
    @GetMapping("/earnings/year/{parkingId}")
    public ResponseEntity<Double> getEarningsForYear(@PathVariable UUID parkingId) {
        double earnings = statsService.getEarningsForYear(parkingId);
        return ResponseEntity.ok(earnings);
    }
}
