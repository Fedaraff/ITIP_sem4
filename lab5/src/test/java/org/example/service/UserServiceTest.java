package org.example.service;

import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.model.enums.UserRole;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        // Подготовка данных
        UserDto dto = UserDto.builder()
                .name("Иван Иванов")
                .email("ivan@example.com")
                .password("123456")
                .phone("+79990001122")
                .build();

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(dto.getName());
        savedUser.setEmail(dto.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setRole(UserRole.ROLE_USER);

        // Настройка mock
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Вызов метода
        User result = userService.createUser(dto);

        // Проверки
        assertNotNull(result);
        assertEquals("Иван Иванов", result.getName());
        assertEquals("ivan@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldGetUserById() {
        // Подготовка
        User user = new User();
        user.setId(1L);
        user.setName("Тестовый пользователь");
        user.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Вызов
        User result = userService.getUserById(1L);

        // Проверки
        assertNotNull(result);
        assertEquals("Тестовый пользователь", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(999L));
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        when(userRepository.existsById(999L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(999L));
    }
}