package com.nelumbo.parking.controller;

import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.service.impl.VehicleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    public final VehicleServiceImpl vehicleService;

    @GetMapping(path = "/{vehiclePlate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDto> getByVehiclePlate(@PathVariable String vehiclePlate){
        return ResponseEntity.ok(vehicleService.findByVehiclePlate(vehiclePlate).get());
    }

}
