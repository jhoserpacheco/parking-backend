package com.nelumbo.user.service;

import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface IUserService {
    void changeRol(String email, int idRol) throws Exception;
    User save(User user) throws Exception;
    Optional<User> findByEmail(String email);
    List<UserDto> findAll();
    Optional<UserDto> findById(UUID id);
    void disableByEmail(String email);
    List<UserDto> findAllbyRol(int idRol);

}

