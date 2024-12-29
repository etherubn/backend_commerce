package com.catdog.comerce.utils;

import com.catdog.comerce.entity.Role;
import com.catdog.comerce.entity.User;
import com.catdog.comerce.enums.RoleType;
import com.catdog.comerce.repository.RoleRepo;
import com.catdog.comerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;


    @Override
    public void run(String... args) throws Exception {
        if (userRepo.findOneByUsername("admin").isEmpty()){

            Role roleAdmin = roleRepo.findByType(RoleType.ADMIN)
                    .orElseGet(()-> roleRepo.save(Role.builder().type(RoleType.ADMIN).build()));

            Role roleUser = roleRepo.findByType(RoleType.USER)
                    .orElseGet(()-> roleRepo.save(Role.builder().type(RoleType.USER).build()));

            Set<Role> roles = new HashSet<>();
            roles.add(roleAdmin);
            roles.add(roleUser);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("12345"));
            admin.setRole(roles);
            admin.setEmail("moreno.greenwater@gmail.com");
            userRepo.save(admin);
        }
    }
}
