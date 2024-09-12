package com.nelumbo.user.repository;

import com.nelumbo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findById(UUID id);
    Optional<User> findByVerificationCode(String verificationCode);
    List<User> findAllByRolId(int idRol);
}
