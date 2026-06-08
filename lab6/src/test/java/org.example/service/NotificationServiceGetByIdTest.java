package org.example.service;

import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceGetByIdTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldGetNotificationById() {
        Long notificationId = 1L;
        User user = new User();
        user.setId(1L);

        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setTitle("Тестовое уведомление");
        notification.setMessage("Тестовое сообщение");
        notification.setChannel(NotificationChannel.EMAIL);
        notification.setStatus(NotificationStatus.CREATED);
        notification.setRecipient(user);

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getNotificationById(notificationId);

        assertNotNull(result);
        assertEquals(notificationId, result.getId());
        assertEquals("Тестовое уведомление", result.getTitle());
        verify(notificationRepository, times(1)).findById(notificationId);
    }
}