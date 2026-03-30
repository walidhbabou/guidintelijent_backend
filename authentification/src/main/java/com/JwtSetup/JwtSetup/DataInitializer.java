package com.JwtSetup.JwtSetup;


import com.JwtSetup.JwtSetup.entity.Role;
import com.JwtSetup.JwtSetup.entity.User;

import com.JwtSetup.JwtSetup.repo.RoleRepository;
import com.JwtSetup.JwtSetup.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Créer un rôle ADMIN si non existant
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        // Créer un rôle USER si non existant
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        // Créer un utilisateur admin si non existant
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@appguid.local");
            admin.setFullName("Administrator");
            admin.setPhone("0000000000");
            admin.setPassword(passwordEncoder.encode("2002"));
            admin.setEnabled(true);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN").get());
            admin.setRoles(roles);
            userRepository.save(admin);
            System.out.println("Utilisateur admin créé !");
        }

        // Créer un utilisateur standard si non existant
        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@appguid.local");
            user.setFullName("Standard User");
            user.setPhone("0000000001");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setEnabled(true);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_USER").get());
            user.setRoles(roles);
            userRepository.save(user);
            System.out.println("Utilisateur user créé !");
        }

        if (userRepository.findByUsername("sophie").isEmpty()) {
            User guideUser = new User();
            guideUser.setUsername("sophie");
            guideUser.setEmail("sophie@guide.ma");
            guideUser.setFullName("Sophie Dubois");
            guideUser.setPhone("0600000000");
            guideUser.setPassword(passwordEncoder.encode("123456"));
            guideUser.setEnabled(true);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_USER").get());
            guideUser.setRoles(roles);
            userRepository.save(guideUser);
            System.out.println("Utilisateur sophie cree !");
        }
    }
}
