package com.JwtSetup.JwtSetup.service;

import com.JwtSetup.JwtSetup.entity.Role;
import com.JwtSetup.JwtSetup.entity.User;
import com.JwtSetup.JwtSetup.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setPassword("secret");

        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Set.of(role));

        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername("john");

        assertEquals("john", result.getUsername());
        assertEquals("secret", result.getPassword());
        assertEquals(1, result.getAuthorities().size());
        assertEquals("ROLE_USER", result.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void loadUserByUsername_shouldThrow_whenUserDoesNotExist() {
        when(userRepository.findByUsername("missing")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("missing")
        );

        assertEquals("User not found: missing", exception.getMessage());
    }
}
