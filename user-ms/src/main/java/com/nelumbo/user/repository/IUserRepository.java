package com.nelumbo.user.repository;

import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.entity.User;
import com.nelumbo.user.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    /*@Query(value = "SELECT u.full_name as fullName, u.email, r.name as rol " +
            "FROM users" +
            "WHERE ",
            countQuery = "SELECT (*) count FROM users",
            nativeQuery = true
    )
    Page<UserProjection> findAllByRolId(int idRol, Pageable pageable);
    */@Query(value = "SELECT u.full_name as fullName, u.email, r.name as rol " +
            "FROM users",
            countQuery = "SELECT (*) count FROM users",
            nativeQuery = true
    )
    Page<UserProjection> findAllDto(Pageable pageable);
}
