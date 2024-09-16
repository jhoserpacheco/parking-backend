package com.nelumbo.mail.feign;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String fullName;
    private String email;
    private String rol;
    private boolean enabled;
}
