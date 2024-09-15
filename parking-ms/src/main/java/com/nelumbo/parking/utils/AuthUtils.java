package com.nelumbo.parking.utils;

import com.nelumbo.parking.feign.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class AuthUtils {

    public String getEmailAuthentication(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDto.getEmail();
    }
    public String getRolAuthentication(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDto.getEmail();
    }
    public UserDto getUserAuthentication(){
        UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDto;
    }
}
