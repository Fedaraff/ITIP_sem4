package org.example.service;

import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceDeleteTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCallDeleteOnRepositoryWhenDeletingUser() {
        Long userId = 1L;

        // Используем existsById (как в вашей реальной реализации)
        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        Long userId = 999L;

        when(userRepository.existsById(userId)).thenReturn(false);

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(any());
    }
}