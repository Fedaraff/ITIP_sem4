package org.example.service;

import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifyTimesTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldVerifyExactNumberOfCalls() {
        UserDto dto = UserDto.builder()
                .name("Тест")
                .email("test@example.com")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(new User());

        userService.createUser(dto);

        // Проверяем, что save был вызван ровно 1 раз
        verify(userRepository, times(1)).save(any(User.class));

        // Проверяем, что другие методы не вызывались
        verify(userRepository, never()).findById(anyLong());
        verify(userRepository, never()).findAll();
    }
}