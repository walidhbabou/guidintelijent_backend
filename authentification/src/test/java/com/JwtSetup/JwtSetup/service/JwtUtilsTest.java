package com.JwtSetup.JwtSetup.service;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils(passwordEncoder);
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "test-secret-key");
        ReflectionTestUtils.setField(jwtUtils, "jwtAccessExpirationMs", 60_000);
        ReflectionTestUtils.setField(jwtUtils, "jwtRefreshExpirationMs", 120_000);
    }

    @Test
    void generateAndValidateAccessToken_shouldSucceed() {
        Authentication authentication = buildAuthentication("alice");

        String token = jwtUtils.generateAccessToken(authentication);

        assertTrue(jwtUtils.validateJwtToken(token));
        assertEquals("alice", jwtUtils.getUsernameFromToken(token));
    }

    @Test
    void generateAccessToken_withInvalidPrincipal_shouldThrow() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("alice", "pwd");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jwtUtils.generateAccessToken(authentication)
        );

        assertEquals("Principal is not an instance of UserDetails", exception.getMessage());
    }

    @Test
    void isRefreshToken_shouldReturnTrueForRefreshAndFalseForAccessToken() {
        Authentication authentication = buildAuthentication("bob");

        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        assertFalse(jwtUtils.isRefreshToken(accessToken));
        assertTrue(jwtUtils.isRefreshToken(refreshToken));
    }

    @Test
    void hashAndValidateRefreshToken_shouldDelegateToPasswordEncoder() {
        when(passwordEncoder.encode("raw-token")).thenReturn("encoded-token");
        when(passwordEncoder.matches("raw-token", "encoded-token")).thenReturn(true);

        String hashed = jwtUtils.hashRefreshToken("raw-token");
        boolean valid = jwtUtils.validateRefreshToken("raw-token", "encoded-token");

        assertEquals("encoded-token", hashed);
        assertTrue(valid);
        verify(passwordEncoder).encode("raw-token");
        verify(passwordEncoder).matches("raw-token", "encoded-token");
    }

    @Test
    void validateJwtToken_shouldReturnFalseForTamperedToken() {
        String token = Jwts.builder()
                .setSubject("eve")
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, "another-secret")
                .compact();

        assertFalse(jwtUtils.validateJwtToken(token));
    }

    private Authentication buildAuthentication(String username) {
        User principal = new User(username, "pwd", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }
}
