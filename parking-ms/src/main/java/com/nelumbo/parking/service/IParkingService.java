package com.nelumbo.parking.service;

import com.nelumbo.parking.dto.ParkingDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface IParkingService {

    ParkingDto save(ParkingDto parking, String token);
    Optional<ParkingDto> findById(UUID id);
    ParkingDto update(UUID idParking, ParkingDto parking, String token);
    String delete(UUID idParking);
    void updateCurrentCapacity(UUID idParking);
    List<ParkingDto> findAll();
    List<ParkingDto> findAllBySocio(String emailUser);

}
