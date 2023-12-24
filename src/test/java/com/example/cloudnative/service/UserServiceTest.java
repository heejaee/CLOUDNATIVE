package com.example.cloudnative.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cloudnative.domain.CloudUser;
import com.example.cloudnative.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @Rollback(value = false)
    void testCreateUser() {
        // Arrange
        String username = "john_doe";
        String email = "john@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword123";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // Act
        CloudUser createdUser = userService.create(username, email, password);
        // Assert
        assertNotNull(createdUser);
        assertEquals(username, createdUser.getUsername());
        assertEquals(email, createdUser.getEmail());
        assertEquals(encodedPassword, createdUser.getPassword());


        // Verify that userRepository.save() was called with the correct user
        verify(userRepository, times(1)).save(createdUser);
    }

    @Test
    void testFindUserByUsername() {
        // Arrange
        String username = "john_doe";
        CloudUser mockUser = new CloudUser(username, "john@example.com", "encodedPassword123");

        when(userRepository.findByusername(username)).thenReturn(Optional.of(mockUser));

        // Act
        CloudUser foundUser = userService.findUser(username);

        // Assert
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());

        // Verify that userRepository.findByUsername() was called with the correct username
        verify(userRepository, times(1)).findByusername(username);
    }

    @Test
    void testFindUserByUsernameNotFound() {
        // Arrange
        String username = "nonexistent_user";

        when(userRepository.findByusername(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(DataNotFoundException.class, () -> userService.findUser(username));

        // Verify that userRepository.findByUsername() was called with the correct username
        verify(userRepository, times(1)).findByusername(username);
    }
}
