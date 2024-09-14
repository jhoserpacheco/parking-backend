package com.nelumbo.parking.repository;

import com.nelumbo.parking.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, UUID> {

    Optional<Vehicle> findByVehiclePlate(String plate);

}
