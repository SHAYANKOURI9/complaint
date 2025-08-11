package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, UserDto> users = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserService() {
        // Initialize with demo users
        UserDto user1 = new UserDto();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        users.put(1L, user1);

        UserDto user2 = new UserDto();
        user2.setId(2L);
        user2.setName("Jane Smith");
        user2.setEmail("jane.smith@example.com");
        users.put(2L, user2);

        idCounter.set(3L);
    }

    public List<UserDto> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public UserDto getUserById(Long id) {
        return users.get(id);
    }

    public UserDto createUser(UserDto userDto) {
        userDto.setId(idCounter.getAndIncrement());
        users.put(userDto.getId(), userDto);
        return userDto;
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        if (users.containsKey(id)) {
            userDto.setId(id);
            users.put(id, userDto);
            return userDto;
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        return users.remove(id) != null;
    }
}