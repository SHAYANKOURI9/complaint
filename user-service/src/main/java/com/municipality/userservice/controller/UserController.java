package com.municipality.userservice.controller;

import com.municipality.userservice.model.User;
import com.municipality.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/profile")
    @PreAuthorize("hasRole('CITIZEN') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.getUserByUsername(username);
        
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("name", user.getName());
        profile.put("email", user.getEmail());
        profile.put("username", user.getUsername());
        profile.put("role", user.getRole());
        profile.put("createdAt", user.getCreatedAt());
        
        return ResponseEntity.ok(profile);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}