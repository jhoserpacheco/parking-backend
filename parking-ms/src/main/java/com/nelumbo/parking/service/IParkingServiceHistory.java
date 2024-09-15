package com.nelumbo.parking.service;

import com.nelumbo.parking.dto.ParkingDto;
import com.nelumbo.parking.dto.ParkingHistoryDto;
import com.nelumbo.parking.dto.RegisterParkingDto;
import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.entity.ParkingHistory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface IParkingServiceHistory {
    RegisterParkingDto registerEntry(VehicleDto vehicleDto, UUID parkingId);
    RegisterParkingDto registerExit(VehicleDto vehicle, UUID parkingId);
    ParkingHistory save(ParkingDto parking, VehicleDto vehicle);
    ParkingHistoryDto update(ParkingHistoryDto parkingHistoryDto);
}
