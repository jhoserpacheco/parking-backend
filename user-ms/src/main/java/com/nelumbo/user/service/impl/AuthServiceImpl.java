package com.nelumbo.user.service.impl;

import com.nelumbo.user.dto.LoginUserDto;
import com.nelumbo.user.dto.RegisterUserDto;
import com.nelumbo.user.dto.UserDto;
import com.nelumbo.user.dto.VerifyUserDto;
import com.nelumbo.user.entity.Rol;
import com.nelumbo.user.entity.User;
import com.nelumbo.user.exceptions.UserNotFoundException;
import com.nelumbo.user.repository.IRolRepository;
import com.nelumbo.user.service.IAuthService;
import com.nelumbo.user.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userServiceImpl;
    private final IRolRepository roleRepository;
    private final EmailServiceImpl emailServiceImpl;
    private final JwtServiceImpl jwtServiceImpl;
    private final UserDetailsService userDetailsService;


    private static final int ROL_USER_ID = 1;

    @Override
    public User signup(RegisterUserDto input) {
        User user = new User(input.getFullName(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
        user.setVerificationCode(generateVerificationCode());
        Optional<Rol> rolUser = roleRepository.findById(ROL_USER_ID);
        rolUser.ifPresent(user::setRol);
        user.setVerificationExp(LocalDateTime.now().plusMinutes(15));
        User userSaved = userServiceImpl.save(user);
        //sendVerificationEmail(userSaved);
        return userSaved;
    }

    @Override
    public User authenticate(LoginUserDto input) throws BadRequestException {
        User user = userServiceImpl.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserNotFoundException(Constants.Message.USER_NOT_FOUND));

        if (!user.isEnabled()) {
            throw new BadRequestException(Constants.Message.ACCOUNT_NOT_VERIFIED);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return user;
    }

    @Override
    public void verifyUser(VerifyUserDto input) throws BadRequestException {
        User user = userServiceImpl.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserNotFoundException(Constants.Message.USER_NOT_FOUND));

        if (isVerificationCodeExpired(user)) {
            throw new BadRequestException(Constants.Message.CODE_EXPIRED);
        }

        if (!user.getVerificationCode().equals(input.getVerificationCode())) {
            throw new BadRequestException(Constants.Message.CODE_INVALID);
        }
        enableUser(user);
    }

    private void enableUser(User user)  {
            user.setEnabled(true);
            user.setVerificationCode(null);
            user.setVerificationExp(null);
            userServiceImpl.save(user);
    }

    private boolean isVerificationCodeExpired(User user) {
        return user.getVerificationExp().isBefore(LocalDateTime.now());
    }

    @Override
    public void resendVerificationCode(String email) throws BadRequestException {
        Optional<User> optionalUser = userServiceImpl.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new BadRequestException(Constants.Message.ACCOUNT_ALREADY_VERIFIED);
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationExp(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userServiceImpl.save(user);
        } else {
            throw new UserNotFoundException(Constants.Message.USER_NOT_FOUND);
        }
    }

    private void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = Constants.EmailTemplate.generateVerificationEmail(user.getFullName(), verificationCode);
        emailServiceImpl.sendEmail(user.getEmail(), subject, htmlMessage);
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(100000, 1000000);
        return String.format("%06d", code);
    }

    private boolean validateToken(String token) {
        String jwt = token.substring(7); // Remover "Bearer " del token
        String username = extractTokenUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return jwtServiceImpl.isTokenValid(jwt, userDetails);
    }

    @Override
    public UserDto validate(String token) throws BadRequestException {
        String username = extractTokenUsername(token);
        User user = userServiceImpl.findByEmail(username).orElseThrow(() -> new UserNotFoundException(Constants.Message.USER_NOT_FOUND));
        if (validateToken(token)) {
            return UserDto.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .rol(user.getRol().getName())
                    .build();
        }else {
            throw new BadRequestException(Constants.Message.TOKEN_INVALID);
        }

    }

    private String extractTokenUsername(String token){
        if (token.startsWith("Bearer")) {
            token = token.substring(7);
        }
        return jwtServiceImpl.extractUsername(token);
    }


}
