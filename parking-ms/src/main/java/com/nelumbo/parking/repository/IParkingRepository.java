package com.nelumbo.parking.repository;

import com.nelumbo.parking.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IParkingRepository  extends JpaRepository<Parking, UUID> {

}
