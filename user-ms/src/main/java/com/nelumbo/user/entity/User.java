package com.nelumbo.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nelumbo.user.utils.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.apache.coyote.BadRequestException;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User implements UserDetails {

    private final static String EMAIL_ADMIN = "admin@mail.com";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_exp")
    private LocalDateTime verificationExp;

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public User(String fullName, String email, String password, Rol rol) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((getRol().getName())));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    public void isAdminDefault() throws BadRequestException {
        if (getEmail().equals(EMAIL_ADMIN)){
            throw new BadRequestException(Constants.Message.IS_ADMIN_DEFAULT);
        }
    }

}
