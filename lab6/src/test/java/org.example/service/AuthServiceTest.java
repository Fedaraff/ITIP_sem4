package org.example.service;

import org.example.model.dto.RegisterRequest;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUserSuccessfully() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Иван Иванов");
        request.setEmail("ivan@example.com");
        request.setPassword("qwerty123");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> authService.register(request));

        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(passwordEncoder, times(1)).encode(request.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Иван Иванов");
        request.setEmail("existing@example.com");
        request.setPassword("qwerty123");

        User existingUser = new User();
        existingUser.setEmail("existing@example.com");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> authService.register(request));

        assertEquals("Пользователь с таким email уже существует", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}