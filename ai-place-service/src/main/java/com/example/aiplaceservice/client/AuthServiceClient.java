package com.example.aiplaceservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthServiceClient {

    @PostMapping("/api/auth/validate")
    ResponseEntity<TokenValidationResponse> validateToken(
            @RequestHeader("Authorization") String token
    );
}
