package org.example.service;

import org.example.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceNotFoundTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldThrowExceptionWhenNotificationNotFound() {
        Long notificationId = 999L;

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.getNotificationById(notificationId));
        verify(notificationRepository, times(1)).findById(notificationId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentNotification() {
        Long notificationId = 999L;

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.deleteNotification(notificationId));
        verify(notificationRepository, times(1)).findById(notificationId);
        verify(notificationRepository, never()).delete(any());
    }
}