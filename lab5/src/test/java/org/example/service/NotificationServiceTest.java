package org.example.service;

import org.example.model.dto.NotificationDto;
import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.repository.NotificationRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldCreateNotification() {
        // Подготовка пользователя
        User user = new User();
        user.setId(1L);
        user.setEmail("ivan@example.com");

        // Подготовка DTO
        NotificationDto dto = NotificationDto.builder()
                .title("Напоминание")
                .message("Завтра занятие по Spring")
                .channel(NotificationChannel.EMAIL)
                .recipientId(1L)
                .build();

        // Подготовка сохранённого уведомления
        Notification savedNotification = new Notification();
        savedNotification.setId(1L);
        savedNotification.setTitle(dto.getTitle());
        savedNotification.setMessage(dto.getMessage());
        savedNotification.setChannel(dto.getChannel());
        savedNotification.setStatus(NotificationStatus.CREATED);
        savedNotification.setRecipient(user);

        // Настройка mock
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class))).thenReturn(savedNotification);

        // Вызов метода
        Notification result = notificationService.createNotification(dto);

        // Проверки
        assertNotNull(result);
        assertEquals("Напоминание", result.getTitle());
        assertEquals(NotificationChannel.EMAIL, result.getChannel());
        assertEquals(NotificationStatus.CREATED, result.getStatus());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundForNotification() {
        NotificationDto dto = NotificationDto.builder()
                .title("Тест")
                .message("Сообщение")
                .channel(NotificationChannel.EMAIL)
                .recipientId(999L)
                .build();

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.createNotification(dto));
    }

    @Test
    void shouldGetNotificationById() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setTitle("Тестовое уведомление");

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getNotificationById(1L);

        assertNotNull(result);
        assertEquals("Тестовое уведомление", result.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenNotificationNotFound() {
        when(notificationRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.getNotificationById(999L));
    }
}