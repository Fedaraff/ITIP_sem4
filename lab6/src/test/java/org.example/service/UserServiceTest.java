package org.example.service;

import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.model.enums.UserRole;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private Long testUserId = 1L;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(testUserId);
        testUser.setName("Иван Иванов");
        testUser.setEmail("ivan@example.com");
        testUser.setPhone("+79990001122");
        testUser.setPassword("password123");
        testUser.setRole(UserRole.ROLE_USER);
        testUser.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateUser() {
        UserDto dto = UserDto.builder()
                .name("Иван Иванов")
                .email("ivan@example.com")
                .phone("+79990001122")
                .password("password123")
                .role(UserRole.ROLE_USER)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals("Иван Иванов", result.getName());
        assertEquals("ivan@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(testUserId);

        assertNotNull(result);
        assertEquals(testUserId, result.getId());
        assertEquals("Иван Иванов", result.getName());
        verify(userRepository, times(1)).findById(testUserId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Long nonExistentId = 999L;

        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(nonExistentId));
        verify(userRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void shouldDeleteUser() {
        // Для deleteUser используем existsById, если ваша реализация такая
        when(userRepository.existsById(testUserId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(testUserId);

        userService.deleteUser(testUserId);

        verify(userRepository, times(1)).existsById(testUserId);
        verify(userRepository, times(1)).deleteById(testUserId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        Long nonExistentId = 999L;

        // Для несуществующего пользователя existsById возвращает false
        when(userRepository.existsById(nonExistentId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(nonExistentId));

        verify(userRepository, times(1)).existsById(nonExistentId);
        verify(userRepository, never()).deleteById(any());
    }
}