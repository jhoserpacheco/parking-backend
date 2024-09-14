package com.nelumbo.parking.repository;

import com.nelumbo.parking.entity.ParkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IParkingHistoryRepository extends JpaRepository<ParkingHistory, UUID> {

    Optional<ParkingHistory> findByVehicleVehiclePlateAndParkingIdAndExitDateIsNull(String plate, UUID parkingId);
    Optional<ParkingHistory> findByVehicleVehiclePlateAndExitDateIsNull(String plate);


}
