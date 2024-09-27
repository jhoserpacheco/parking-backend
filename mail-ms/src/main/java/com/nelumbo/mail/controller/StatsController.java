package com.nelumbo.mail.controller;

import com.nelumbo.mail.dto.EmailCountDto;
import com.nelumbo.mail.dto.ParkingCountDto;
import com.nelumbo.mail.dto.VehicleCountDto;
import com.nelumbo.mail.service.impl.StatsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsServiceImpl statsService;

    @GetMapping(path = "/find-top-10-vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleCountDto>> getTop10Vehicles(){
        return ResponseEntity.ok(statsService.findTop10VehicleRegistered());
    }
    @GetMapping(path = "/find-top-10-most-registered-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmailCountDto>> getTop10Email(){
        return ResponseEntity.ok(statsService.findTop10UserMostRegisteredToEmail());
    }

    @GetMapping(path = "/find-top-10-most-registered-parking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParkingCountDto>> getTop10Parking(){
        return ResponseEntity.ok(statsService.findTop10UserMostRegisteredParking());
    }
}
