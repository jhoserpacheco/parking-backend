package com.nelumbo.mail.repository;

import com.nelumbo.mail.entity.EmailParking;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmailParkingRepository extends MongoRepository<EmailParking, ObjectId> {
    List<EmailParking> findAllByVehiclePlate(String vehiclePlate);
    List<EmailParking> findAllByStatus(String status);
}
