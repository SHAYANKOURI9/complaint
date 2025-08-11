package com.example.authservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void testAuthenticateWithValidCredentials() {
        String token = authService.authenticate("admin", "admin123");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testAuthenticateWithInvalidCredentials() {
        assertThrows(RuntimeException.class, () -> {
            authService.authenticate("admin", "wrongpassword");
        });
    }

    @Test
    void testValidateTokenWithValidToken() {
        String token = authService.authenticate("user", "user123");
        boolean isValid = authService.validateToken(token);
        assertTrue(isValid);
    }

    @Test
    void testValidateTokenWithInvalidToken() {
        boolean isValid = authService.validateToken("invalid.token.here");
        assertFalse(isValid);
    }
}