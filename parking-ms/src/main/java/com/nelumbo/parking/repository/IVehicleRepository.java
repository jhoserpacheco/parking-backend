package com.nelumbo.parking.repository;

import com.nelumbo.parking.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, UUID> {

    Optional<Vehicle> findByVehiclePlate(String plate);

    // top10 vehiculos mas registrados en los parqueaderos
    List<Vehicle> findTop10ByOrderByTotalVisitDesc();

    // top10 vehiculos que se han regisrtado en x parqueadero y cuantas veces
    List<Vehicle> findTop10ByParkingIdOrderByTotalVisitDesc(UUID idParking);

    // verificar en cada parqueadero cual esta por 1ra vez
    List<Vehicle> findAllByTotalVisit(int totalVisit);

}
