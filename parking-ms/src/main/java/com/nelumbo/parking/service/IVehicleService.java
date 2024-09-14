package com.nelumbo.parking.service;

import com.nelumbo.parking.dto.VehicleDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IVehicleService {
    VehicleDto save(VehicleDto vehicle);
    Optional<VehicleDto> findByVehiclePlate(String vehiclePlate);
    VehicleDto updateVisit(String vehiclePlate);
}
