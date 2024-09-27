package com.nelumbo.mail.repository;

import com.nelumbo.mail.entity.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepository extends MongoRepository<Email, UUID> {

}