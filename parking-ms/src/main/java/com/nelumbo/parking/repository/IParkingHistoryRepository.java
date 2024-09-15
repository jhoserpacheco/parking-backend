package com.nelumbo.parking.repository;

import com.nelumbo.parking.entity.ParkingHistory;
import feign.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IParkingHistoryRepository extends JpaRepository<ParkingHistory, UUID> {

    Optional<ParkingHistory> findByVehicleVehiclePlateAndParkingIdAndExitDateIsNull(String plate, UUID parkingId);
    Optional<ParkingHistory> findByVehicleVehiclePlateAndExitDateIsNull(String plate);

    // top10 vehiculos mas registrados en los parqueaderos [V]
    List<ParkingHistory> findTop10ByOrderByVehicleTotalVisitDesc();
    // top10 vehiculos que se han regisrtado en x parqueadero y cuantas veces [V]
    List<ParkingHistory> findTop10ByParkingIdOrderByVehicleTotalVisitDesc(UUID parkingId);
    // verificar en cada parqueadero cual esta por 1ra vez [V]
    List<ParkingHistory> findAllByVehicleTotalVisitOrderByVehicleTotalVisitDesc(int totalVisit);
    // socio
    // ganancias de hoy, semana, mes, año de x parquadero
    @Query("SELECT SUM(p.totalCost) FROM ParkingHistory p WHERE p.parking.id = :parkingId AND p.exitDate BETWEEN :startDate AND :endDate")
    Double getTotalEarnings(@Param("parkingId") UUID parkingId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    //admin
    // top 3 socios con mas ingresos de vehiculos en la semana actual y mostrar cantidad vehiculos
    @Query("SELECT p.parking.emailUser, COUNT(p.vehicle) AS vehicleCount " +
            "FROM ParkingHistory p " +
            "WHERE p.exitDate BETWEEN :startDate AND :endDate " +
            "GROUP BY p.parking.emailUser " +
            "ORDER BY vehicleCount DESC")
    List<Object[]> getTop3UsersWithMostVehicles(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    // top 3 de parqueaderos con mayor ganancia
    @Query("SELECT p.parking.id, p.parking.name, p.parking.emailUser,  " +
            "SUM(p.totalCost) AS totalEarnings FROM ParkingHistory p " +
            "WHERE p.exitDate BETWEEN :startDate AND :endDate " +
            "GROUP BY p.parking.id, p.parking.name, p.parking.emailUser " +
            "ORDER BY totalEarnings DESC")
    List<Object[]> getTop3ParkingsWithHighestEarnings(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);




}
