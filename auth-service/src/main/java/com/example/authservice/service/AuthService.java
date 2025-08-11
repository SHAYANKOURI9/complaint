package com.example.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // In-memory user store for demo purposes
    private final Map<String, String> users = new HashMap<>();

    public AuthService() {
        // Initialize with demo users
        users.put("admin", "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa"); // password: admin123
        users.put("user", "$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a"); // password: user123
    }

    public String authenticate(String username, String password) {
        String storedPassword = users.get(username);
        
        if (storedPassword != null && passwordEncoder.matches(password, storedPassword)) {
            return jwtService.generateToken(username);
        }
        
        throw new RuntimeException("Invalid credentials");
    }

    public boolean validateToken(String token) {
        try {
            String username = jwtService.extractUsername(token);
            return users.containsKey(username) && jwtService.isTokenValid(token, username);
        } catch (Exception e) {
            return false;
        }
    }
}