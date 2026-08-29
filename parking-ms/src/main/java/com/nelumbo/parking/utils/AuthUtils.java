package com.nelumbo.parking.utils;

import com.nelumbo.parking.feign.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Componente de utilidad para extraer la información del usuario autenticado en el SecurityContext.
 */
@Component
public class AuthUtils {

    public String getEmailAuthentication() {
        UserDto userDto = getUserAuthentication();
        return userDto != null ? userDto.getEmail() : "";
    }

    public String getRolAuthentication() {
        UserDto userDto = getUserAuthentication();
        return userDto != null ? userDto.getRol() : "";
    }

    public UserDto getUserAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDto) {
            return (UserDto) authentication.getPrincipal();
        }
        return null;
    }
}
