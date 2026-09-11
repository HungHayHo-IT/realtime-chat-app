package com.ChatApp.service;

import com.ChatApp.dto.RegisterRequest;
import com.ChatApp.entity.User;
import com.ChatApp.exception.DuplicateResourceException;
import com.ChatApp.service.AuthService;
import com.ChatApp.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest request;

    @BeforeEach
    void setUp() {

        request = new RegisterRequest();
        request.setUsername("hungdev");
        request.setEmail("hung@gmail.com");
        request.setPassword("123456");
    }

    @Test
    void register_shouldCreateUserSuccessfully() {

        when(userService.existsByUsername("hungdev"))
                .thenReturn(false);

        when(userService.existsByEmail("hung@gmail.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("hashed-password");

        User savedUser = User.builder()
                .id(1L)
                .username("hungdev")
                .email("hung@gmail.com")
                .password("hashed-password")
                .build();

        when(userService.createUser(any(User.class)))
                .thenReturn(savedUser);

        User result = authService.register(request);

        assertNotNull(result);
        assertEquals("hungdev", result.getUsername());
        assertEquals("hung@gmail.com", result.getEmail());
        assertEquals("hashed-password", result.getPassword());

        verify(passwordEncoder).encode("123456");
        verify(userService).createUser(any(User.class));
    }

    @Test
    void register_shouldThrowException_whenUsernameExists() {

        when(userService.existsByUsername("hungdev"))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> authService.register(request)
        );

        verify(userService, never())
                .createUser(any(User.class));

        verify(passwordEncoder, never())
                .encode(anyString());
    }

    @Test
    void register_shouldThrowException_whenEmailExists() {

        when(userService.existsByUsername("hungdev"))
                .thenReturn(false);

        when(userService.existsByEmail("hung@gmail.com"))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> authService.register(request)
        );

        verify(userService, never())
                .createUser(any(User.class));
    }
}