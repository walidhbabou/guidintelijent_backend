// Nouveau contrôleur UserController pour tester avec findAll
package com.JwtSetup.JwtSetup.controller;

import com.JwtSetup.JwtSetup.entity.User;
import com.JwtSetup.JwtSetup.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Protège l'accès avec JWT
    public List<User> getAllUsers() {
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }
}
