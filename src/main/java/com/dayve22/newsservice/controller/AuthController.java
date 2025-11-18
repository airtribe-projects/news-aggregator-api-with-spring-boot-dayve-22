package com.dayve22.newsservice.controller;

import com.dayve22.newsservice.dto.AuthResponse;
import com.dayve22.newsservice.dto.LoginRequest;
import com.dayve22.newsservice.dto.RegisterRequest;
import com.dayve22.newsservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        // The AuthService handles logic and token generation
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        // The AuthService handles authentication and token generation
        return ResponseEntity.ok(authService.login(request));
    }
}