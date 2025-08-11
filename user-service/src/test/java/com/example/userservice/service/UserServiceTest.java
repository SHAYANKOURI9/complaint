package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testGetAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        assertNotNull(users);
        assertTrue(users.size() >= 2); // Should have at least 2 demo users
    }

    @Test
    void testGetUserById() {
        UserDto user = userService.getUserById(1L);
        assertNotNull(user);
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testCreateUser() {
        UserDto newUser = new UserDto();
        newUser.setName("Test User");
        newUser.setEmail("test@example.com");

        UserDto createdUser = userService.createUser(newUser);
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals("Test User", createdUser.getName());
        assertEquals("test@example.com", createdUser.getEmail());
    }

    @Test
    void testUpdateUser() {
        UserDto updateUser = new UserDto();
        updateUser.setName("Updated User");
        updateUser.setEmail("updated@example.com");

        UserDto updatedUser = userService.updateUser(1L, updateUser);
        assertNotNull(updatedUser);
        assertEquals(1L, updatedUser.getId());
        assertEquals("Updated User", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        boolean deleted = userService.deleteUser(2L);
        assertTrue(deleted);
        
        UserDto user = userService.getUserById(2L);
        assertNull(user);
    }
}