package com.nelumbo.user.seeder;

import com.nelumbo.user.entity.Rol;
import com.nelumbo.user.entity.User;
import com.nelumbo.user.repository.IRolRepository;
import com.nelumbo.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSeed implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IRolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadRolData();
        loadUserData();
    }

    private void loadRolData(){
        if (rolRepository.count() == 0){
            Rol rolAdmin = new Rol(3,"ADMIN");
            Rol rolSocio = new Rol(2,"SOCIO");
            Rol rolUser = new Rol(1,"USER");
            List<Rol> roles = List.of(rolAdmin, rolSocio, rolUser);
            rolRepository.saveAll(roles);
            System.out.println("Rol loaded.");
        }else{
            System.out.println("Rol already loaded.");
        }
    }

    private void loadUserData(){
        int idRolAdmin = 3;
        Optional<Rol> rolAmin = rolRepository.findById(idRolAdmin);
        if (rolAmin.isPresent()){
            if (userRepository.count() == 0) {
                User userAdmin = new User("Admin", "admin@mail.com",
                        passwordEncoder.encode("admin"),
                        rolAmin.get());
                User socio1 = new User("Socio 1", "socio1@mail.com",
                        passwordEncoder.encode("socio1"),
                        rolAmin.get());
                User socio2 = new User("Socio 2", "socio2@mail.com",
                        passwordEncoder.encode("socio2"),
                        rolAmin.get());

                userRepository.saveAll(List.of(userAdmin, socio1, socio2));
                System.out.println("Users loaded.");
            }
            else System.out.println("User already loaded.");
        }else{
            loadRolData();
        }
    }
}
