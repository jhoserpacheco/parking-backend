package com.nelumbo.parking.service.impl;

import com.nelumbo.parking.dto.ParkingDto;
import com.nelumbo.parking.dto.ParkingHistoryDto;
import com.nelumbo.parking.dto.RegisterParkingDto;
import com.nelumbo.parking.dto.VehicleDto;
import com.nelumbo.parking.entity.ParkingHistory;
import com.nelumbo.parking.exceptions.ParkingNotFoundException;
import com.nelumbo.parking.exceptions.VehicleNotFoundException;
import com.nelumbo.parking.mapper.IParkingHistoryMapping;
import com.nelumbo.parking.mapper.IParkingMapping;
import com.nelumbo.parking.mapper.IVehicleMapping;
import com.nelumbo.parking.projection.EstimateCostProjection;
import com.nelumbo.parking.repository.IParkingHistoryRepository;
import com.nelumbo.parking.service.IParkingServiceHistory;
import com.nelumbo.parking.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingServiceHistoryImpl implements IParkingServiceHistory {

    private final IParkingHistoryRepository parkingHistoryRepository;
    private final VehicleServiceImpl vehicleService;
    private final ParkingServiceImpl parkingService;
    private final IParkingHistoryMapping parkingHistoryMapping = IParkingHistoryMapping.INSTANCE;
    private final IVehicleMapping vehicleMapping = IVehicleMapping.INSTANCE;
    private final IParkingMapping parkingMapping = IParkingMapping.INSTANCE;

    @Override
    public RegisterParkingDto registerEntry(VehicleDto vehicleDto, UUID parkingId) {
        boolean entry = true;
        Optional<ParkingDto> parking = parkingService.findById(parkingId);
        if (parking.isEmpty()) {
            throw new ParkingNotFoundException(Constants.Message.PARKING_NOT_FOUND);
        }
        parkingService.isParkingSocioAsociated(parkingId);
        Optional<VehicleDto> existingVehicle = vehicleService.findByVehiclePlate(vehicleDto.getVehiclePlate());
        validateRegister(vehicleDto.getVehiclePlate());
        VehicleDto vehicleToUse;
        if (existingVehicle.isPresent()) {
            vehicleDto.setIdParking(parkingId);
            vehicleService.updateParking(vehicleDto.getVehiclePlate(), parkingId);
            vehicleToUse = vehicleService.updateVisit(vehicleDto.getVehiclePlate());
        } else {
            vehicleDto.setIdParking(parkingId);
            vehicleToUse = vehicleService.save(vehicleDto);
        }
        parkingService.updateCurrentCapacity(parkingId, entry);
        ParkingHistory parkingHistory = save(parking.get(), vehicleToUse);
        return new RegisterParkingDto(parkingId, Constants.Message.REGISTER_ENTRY,vehicleToUse.getVehiclePlate(), parkingHistory.getEntryDate());
    }

    @Override
    public ParkingHistory save(ParkingDto parking, VehicleDto vehicle){
        ParkingHistory parkingHistory = new ParkingHistory();
        parkingHistory.setParking(parkingMapping.parkingDtoToParking(parking));
        parkingHistory.setVehicle(vehicleMapping.vehicleDtoToVehicle(vehicle));
        parkingHistory.setEntryDate(LocalDateTime.now());

        return parkingHistoryRepository.save(parkingHistory);
    }

    private void validateRegister(String vehiclePlate){
        Optional<ParkingHistory> parkingHistory = parkingHistoryRepository.
                findByVehicleVehiclePlateAndExitDateIsNull(vehiclePlate);
        if (parkingHistory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,Constants.Message.REGISTER_ENTRY_FAILED);
        }
    }

    @Override
    public RegisterParkingDto registerExit(VehicleDto vehicle, UUID parkingId) {
        boolean entry = false;
        parkingService.isParkingSocioAsociated(parkingId);
        Optional<VehicleDto> vehicleDto = vehicleService.findByVehiclePlate(vehicle.getVehiclePlate());
        if (vehicleDto.isPresent() && vehicleDto.get().getIdParking().equals(parkingId)) {
            Optional<ParkingHistoryDto> parkingHistory = findParkingHistoryByVehiclePlateAndParkingId(
                    vehicleDto.get().getVehiclePlate(), parkingId);
            if (parkingHistory.isPresent()) {
                parkingService.updateCurrentCapacity(parkingId, entry);
                ParkingHistoryDto parkingHistoryDto = update(parkingHistory.get());
                return new RegisterParkingDto(parkingId, Constants.Message.REGISTER_EXIT, vehicleDto.get().getVehiclePlate(), parkingHistoryDto.getExitDate());
            }
        }
        throw new VehicleNotFoundException(Constants.Message.VEHICLE_NO_REGISTER_PARKING);
    }

    @Override
    public ParkingHistoryDto update(ParkingHistoryDto parkingHistoryDto) {
        Optional<ParkingHistory> parking = parkingHistoryRepository
                .findByVehicleVehiclePlateAndParkingIdAndExitDateIsNull(parkingHistoryDto.getVehiclePlate(), parkingHistoryDto.getIdParking());
        if (parking.isPresent()) {
            LocalDateTime entryDate = parkingHistoryDto.getEntryDate();
            LocalDateTime exitDate = LocalDateTime.now();
            parking.get().setExitDate(exitDate);
            parking.get().setTotalCost(calculateCostParking(parking.get().getParking().getCostHour(), entryDate, exitDate));
            ParkingHistory updateParkingHistory = parkingHistoryRepository.save(parking.get());

            return parkingHistoryMapping.parkingHistoryToParkingHistoryDto(updateParkingHistory);
        }
        throw new ParkingNotFoundException(Constants.Message.PARKING_NOT_FOUND);
    }

    public Optional<ParkingHistoryDto> findParkingHistoryByVehiclePlateAndParkingId(String vehiclePlate, UUID parkingId) {
        Optional<ParkingHistory> parkingHistory = parkingHistoryRepository.findByVehicleVehiclePlateAndParkingIdAndExitDateIsNull(vehiclePlate, parkingId);
        return parkingHistory.map(parkingHistoryMapping::parkingHistoryToParkingHistoryDto);
    }

    private double calculateCostParking(double costHour, LocalDateTime entryDate, LocalDateTime exitDate){
        Duration duration = Duration.between(entryDate, exitDate);
        long oneHourInMinutes = 60;
        long totalMin = duration.toMinutes() / oneHourInMinutes;
        long totalHour = (totalMin % 60 == 0) ? totalMin : totalMin + 1;
        return costHour * totalHour;
    }

    public Page<ParkingHistory> getParkingHistorySorted(Pageable pageable, String query) {
        if (query==null) query = "";
        return parkingHistoryRepository.findAllOrdered(pageable, query);
    }

    public EstimateCostProjection getEstimateCostProjection(UUID parkingId) {
        parkingService.isParkingSocioAsociated(parkingId);
        return parkingHistoryRepository.findEstimateCostByParkingId(parkingId);
    }


}
