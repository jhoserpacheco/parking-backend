package com.nelumbo.user.service.impl;

import com.nelumbo.user.dto.ChangeRolDto;
import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.entity.Rol;
import com.nelumbo.user.entity.User;
import com.nelumbo.user.exceptions.UserNotFoundException;
import com.nelumbo.user.mapper.IUserMapping;
import com.nelumbo.user.projection.UserProjection;
import com.nelumbo.user.repository.IRolRepository;
import com.nelumbo.user.repository.IUserRepository;
import com.nelumbo.user.service.IUserService;
import com.nelumbo.user.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IRolRepository roleRepository;

    @Override
    public void changeRol(ChangeRolDto changeRol) throws BadRequestException {
        Optional<User> user = findByEmail(changeRol.getEmail());
        Optional<Rol> rol = roleRepository.findById(changeRol.getIdRol());
        if(user.isPresent() && rol.isPresent()){
            user.get().isAdminDefault();
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

    /*@Override
    public Page<UserProjection> findAllbyRol(int idRol, Pageable pageable){
        return userRepository.findAllByRolId(idRol, pageable);
    }*/

    @Override
    public Page<UserProjection> findAll(Pageable pageable) {
        return userRepository.findAllDto(pageable);
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
