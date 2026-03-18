package com.example.aiplaceservice.security;

import com.example.aiplaceservice.client.AuthServiceClient;
import com.example.aiplaceservice.client.TokenValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthServiceClient authServiceClient;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = extractTokenFromRequest(request);

            if (token != null && !token.isEmpty()) {
                validateTokenWithAuthService(token);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private void validateTokenWithAuthService(String token) throws Exception {
        try {
            ResponseEntity<TokenValidationResponse> response =
                    authServiceClient.validateToken("Bearer " + token);

            if (!response.getStatusCode().is2xxSuccessful() ||
                    response.getBody() == null ||
                    !response.getBody().isValid()) {
                throw new RuntimeException("Token validation failed");
            }
        } catch (Exception e) {
            log.error("Failed to validate token with AUTH-SERVICE: {}", e.getMessage());
            throw new RuntimeException("Token validation failed", e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/actuator") ||
               path.startsWith("/swagger-ui") ||
               path.startsWith("/v3/api-docs") ||
               path.equals("/health") ||
               path.startsWith("/morocco-ai");
    }
}
