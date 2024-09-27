package com.nelumbo.user.service;

import com.nelumbo.user.dto.ChangeRolDto;
import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.entity.User;
import com.nelumbo.user.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface IUserService {
    void changeRol(ChangeRolDto changeRol) throws Exception;
    User save(User user) throws Exception;
    Optional<User> findByEmail(String email);
    Page<UserProjection> findAll(Pageable pageable);
    Optional<UserDto> findById(UUID id);
    void disableByEmail(String email);
    //Page<UserProjection> findAllbyRol(int idRol, Pageable pageable);

}

