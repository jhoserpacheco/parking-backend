package com.nelumbo.parking.service.impl;

import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.entity.Parking;
import com.nelumbo.parking.entity.Vehicle;
import com.nelumbo.parking.exceptions.ParkingNotFoundException;
import com.nelumbo.parking.exceptions.VehicleNotFoundException;
import com.nelumbo.parking.mapper.IVehicleMapping;
import com.nelumbo.parking.repository.IParkingHistoryRepository;
import com.nelumbo.parking.repository.IParkingRepository;
import com.nelumbo.parking.repository.IVehicleRepository;
import com.nelumbo.parking.service.IVehicleService;
import com.nelumbo.parking.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {

    private final IVehicleRepository vehicleRepository;
    private final IParkingRepository parkingRepository;
    private final IParkingHistoryRepository parkingHistoryRepository;
    private final ParkingServiceImpl parkingService;
    private final IVehicleMapping vehicleMapper = IVehicleMapping.INSTANCE;

    @Override
    public VehicleDto save(VehicleDto vehicle) {
        Optional<Parking> parking = parkingRepository.findById(vehicle.getIdParking());
        if (parking.isPresent()) {
            Vehicle newVehicle = vehicleMapper.vehicleDtoToVehicle(vehicle);
            newVehicle.setParking(parking.get());
            return vehicleMapper.vehicleToVehicleDto(vehicleRepository.save(newVehicle));
        }
        throw new ParkingNotFoundException(Constants.Message.PARKING_NOT_FOUND);
    }

    @Override
    public Optional<VehicleDto> findByVehiclePlate(String vehiclePlate) {
        Optional<Vehicle> vehicle = vehicleRepository.findByVehiclePlate(vehiclePlate);
        return vehicle.map(vehicleMapper::vehicleToVehicleDto);
    }

    @Override
    public VehicleDto updateVisit(String vehiclePlate) {
        Optional<Vehicle> vehicleDto = vehicleRepository.findByVehiclePlate(vehiclePlate);
        if (vehicleDto.isPresent()) {
            vehicleDto.get().setTotalVisit(vehicleDto.get().getTotalVisit() + 1);
            vehicleRepository.saveAndFlush(vehicleDto.get());
            return vehicleMapper.vehicleToVehicleDto(vehicleDto.get());
        }
        throw new VehicleNotFoundException(Constants.Message.VEHICLE_NOT_FOUND);

    }

    @Override
    public List<VehicleDto> findAllVehicleActiveParking(UUID parkingId){
        parkingService.isParkingSocioAsociated(parkingId);
        return parkingHistoryRepository.findAllByParkingIdAndExitDateIsNull(parkingId).stream()
                .map(parkingHistory -> vehicleMapper.vehicleToVehicleDto(parkingHistory.getVehicle()))
                .collect(Collectors.toList());
    }

    public void updateParking(String vehiclePlate, UUID idParking) {
        Optional<Parking> parking = parkingRepository.findById(idParking);
        if (parking.isPresent()) {
            Optional<Vehicle> vehicleDto = vehicleRepository.findByVehiclePlate(vehiclePlate);
            if (vehicleDto.isPresent()) {
                vehicleDto.get().setParking(parking.get());
                vehicleRepository.saveAndFlush(vehicleDto.get());
            }else{
                throw new VehicleNotFoundException(Constants.Message.VEHICLE_NOT_FOUND);
            }
        }else {
            throw new ParkingNotFoundException(Constants.Message.PARKING_NOT_FOUND);
        }

    }

}
