package com.JwtSetup.JwtSetup.controller;

import com.JwtSetup.JwtSetup.entity.Role;
import com.JwtSetup.JwtSetup.entity.User;
import com.JwtSetup.JwtSetup.repo.RoleRepository;
import com.JwtSetup.JwtSetup.repo.UserRepository;
import com.JwtSetup.JwtSetup.service.JwtUtils;
import com.JwtSetup.JwtSetup.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public Map<String, String> authenticateUser(@RequestBody Map<String, String> request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.get("username"), request.get("password")));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtUtils.generateAccessToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken(authentication);

            User user = userRepository.findByUsername(request.get("username")).orElseThrow();
            user.setRefreshToken(passwordEncoder.encode(refreshToken));
            userRepository.save(user);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Email or password invalid");
        }
    }

    @PostMapping("/signup")
    public Map<String, String> registerUser(@RequestBody Map<String, String> request) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByUsername(request.get("username")).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Créer le nouvel utilisateur
        User user = new User();
        user.setUsername(request.get("username"));
        user.setPassword(passwordEncoder.encode(request.get("password")));
        user.setEnabled(true);

        // Assigner le rôle USER par défaut
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("username", user.getUsername());
        return response;
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        String refreshToken = refreshTokenHeader.replace("Bearer ", "");

        // Vérifier si le token est bien un refresh token
        if (!jwtUtils.isRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid token type: Expected refresh token");
        }

        String username = jwtUtils.getUsernameFromToken(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow();

        // Vérifier si le refresh token correspond à celui stocké en base (hashé)
        if (!passwordEncoder.matches(refreshToken, user.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String newAccessToken = jwtUtils.generateAccessToken(authentication);
        String newRefreshToken = jwtUtils.generateRefreshToken(authentication);

        user.setRefreshToken(passwordEncoder.encode(newRefreshToken));
        userRepository.save(user);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);
        return tokens;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(@RequestHeader("Authorization") String accessTokenHeader) {
        String accessToken = accessTokenHeader.replace("Bearer ", "");
        String username = jwtUtils.getUsernameFromToken(accessToken);

        User user = userRepository.findByUsername(username).orElseThrow();
        user.setRefreshToken(null); // Supprimer le refresh token en base
        userRepository.save(user);

        SecurityContextHolder.clearContext(); // Nettoyer le contexte de sécurité

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return response;
    }

}
