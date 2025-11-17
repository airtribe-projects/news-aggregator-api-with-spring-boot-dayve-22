package com.dayve22.newsservice.service;

import com.dayve22.newsservice.dto.AuthResponse;
import com.dayve22.newsservice.dto.LoginRequest;
import com.dayve22.newsservice.dto.RegisterRequest;
import com.dayve22.newsservice.model.NewsPreferences;
import com.dayve22.newsservice.model.User;
import com.dayve22.newsservice.repository.UserRepository;
import com.dayve22.newsservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            // We'll add proper exception handling later
            throw new IllegalArgumentException("Username already taken");
        }

        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        // Create default empty preferences
        NewsPreferences preferences = NewsPreferences.builder()
                .user(user)
                .keywords(new ArrayList<>())
                .sources(new ArrayList<>())
                .build();

        // Link user and preferences
        user.setPreferences(preferences);

        // Save user (and preferences due to cascade)
        userRepository.save(user);

        // Generate and return token
        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse login(LoginRequest request) {
        // The AuthenticationManager will use our UserDetailsService and PasswordEncoder
        // to check if the username and password are correct.
        // If not, it throws an AuthenticationException.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // If authentication is successful, find the user
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found after successful authentication"));

        // Generate and return token
        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}
