package com.nelumbo.parking.repository;

import com.nelumbo.parking.entity.Parking;
import com.nelumbo.parking.projection.ParkingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IParkingRepository  extends JpaRepository<Parking, UUID> {

    List<Parking> findAllByEmailUser(String email);

    @Query(value = "SELECT p.name as parkingName, p.current_capacity as currentCapacity, " +
            "p.max_capacity as maxCapacity, p.user_id as emailUser " +
            "FROM parking p",
            countQuery = "SELECT count(*) FROM parking p",
            nativeQuery = true)
    Page<ParkingProjection> findAllProjection(Pageable pageable);

}
