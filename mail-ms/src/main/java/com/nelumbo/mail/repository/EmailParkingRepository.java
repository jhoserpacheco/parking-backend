package com.nelumbo.mail.repository;

import com.nelumbo.mail.entity.EmailParking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmailParkingRepository extends JpaRepository<EmailParking, UUID> {
    List<EmailParking> findAllByVehiclePlate(String vehiclePlate);
    List<EmailParking> findAllByStatus(String status);
}
