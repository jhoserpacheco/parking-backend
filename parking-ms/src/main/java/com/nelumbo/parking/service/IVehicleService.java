package com.nelumbo.parking.service;

import com.nelumbo.parking.dto.VehicleDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface IVehicleService {
    VehicleDto save(VehicleDto vehicle);
    Optional<VehicleDto> findByVehiclePlate(String vehiclePlate);
    VehicleDto updateVisit(String vehiclePlate);
    List<VehicleDto> findAllVehicleActiveParking(UUID parkingId);
}
