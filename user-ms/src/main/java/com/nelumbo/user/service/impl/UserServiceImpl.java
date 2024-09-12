package com.nelumbo.user.service.impl;

import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.entity.Rol;
import com.nelumbo.user.entity.User;
import com.nelumbo.user.exceptions.UserNotFoundException;
import com.nelumbo.user.mapper.IUserMapping;
import com.nelumbo.user.repository.IRolRepository;
import com.nelumbo.user.repository.IUserRepository;
import com.nelumbo.user.service.IUserService;
import com.nelumbo.user.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IRolRepository roleRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void changeRol(String email, int idRol) {
        Optional<User> user = findByEmail(email);
        Optional<Rol> rol = roleRepository.findById(idRol);
        if(user.isPresent() && rol.isPresent()){
            user.get().setRol(rol.get());
            save(user.get());
        }else{
            throw new UserNotFoundException(Constants.Message.USER_NOT_FOUND);
        }
    }

    @Override
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user;
        }
        throw new UserNotFoundException(Constants.Message.USER_NOT_FOUND);
    }

    public UserDto findByEmailDto(String email) {
        Optional<User> user = findByEmail(email);
        if(user.isPresent()){
            return IUserMapping.INSTANCE.userEntityToDto(user.get());
        }
        throw new UserNotFoundException(Constants.Message.USER_NOT_FOUND);
    }

    @Override
    public List<UserDto> findAllbyRol(int idRol){
        return IUserMapping.INSTANCE.toDtoList(userRepository.findAllByRolId(idRol));
    }

    @Override
    public List<UserDto> findAll() {
        return IUserMapping.INSTANCE.toDtoList(userRepository.findAll());
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return Optional.ofNullable(IUserMapping.INSTANCE.userEntityToDto(user.get()));
        }
        return Optional.empty();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void disableByEmail(String email) {
        Optional<User> user = findByEmail(email);
        if(user.isPresent() ){
            user.get().setEnabled(false);
            save(user.get());
        }else{
            throw new UserNotFoundException(Constants.Message.USER_NOT_FOUND);
        }
    }
}
