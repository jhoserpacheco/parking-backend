package com.nelumbo.parking.service.impl;

import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.entity.Vehicle;
import com.nelumbo.parking.mapper.IVehicleMapping;
import com.nelumbo.parking.repository.IVehicleRepository;
import com.nelumbo.parking.service.IVehicleService;
import com.nelumbo.parking.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {

    private final IVehicleRepository vehicleRepository;
    private final IVehicleMapping vehicleMapper = IVehicleMapping.INSTANCE;

    @Override
    public void save(VehicleDto vehicle) {
        if (findByVehiclePlate(vehicle.getVehiclePlate()).isEmpty()) {
            vehicleRepository.save(vehicleMapper.vehicleDtoToVehicle(vehicle));
        }
    }

    @Override
    public Optional<VehicleDto> findByVehiclePlate(String vehiclePlate) {
        Optional<Vehicle> vehicle = vehicleRepository.findByVehiclePlate(vehiclePlate);
        if (vehicle.isPresent()) {
            return Optional.of(vehicleMapper.vehicleToVehicleDto(vehicle.get()));
        }
        throw new RuntimeException(Constants.Message.VEHICLE_NOT_FOUND);
    }

    @Override
    public VehicleDto updateVisit(String vehiclePlate) {
        Optional<VehicleDto> vehicleDto = findByVehiclePlate(vehiclePlate);
        if (vehicleDto.isPresent()) {
            vehicleDto.get().setTotalVisit(vehicleDto.get().getTotalVisit() + 1);
            vehicleRepository.save(vehicleMapper.vehicleDtoToVehicle(vehicleDto.get()));
            return vehicleDto.get();
        }
        throw new RuntimeException(Constants.Message.VEHICLE_NOT_FOUND);

    }

}
