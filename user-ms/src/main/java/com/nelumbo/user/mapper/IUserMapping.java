package com.nelumbo.user.mapper;

import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IUserMapping {

    IUserMapping INSTANCE = Mappers.getMapper( IUserMapping.class );

    @Mapping(source = "rol.name", target = "rol")
    UserDto userEntityToDto(User user);

    @Mapping(source = "rol", target = "rol.name")
    User userDtotoEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);

    List<User> toEntityList(List<UserDto> userDtos);


}
