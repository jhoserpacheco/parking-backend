package com.nelumbo.user.repository;

import com.nelumbo.user.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {
    Rol findByName(String name);
}
