package org.example.controller;

import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void shouldCallGetAllNotifications() {
        notificationController.getAllNotifications();
        verify(notificationService, times(1)).getAllNotifications();
    }

    @Test
    void shouldCallGetNotificationById() {
        Long id = 1L;

        // Создаем полноценный объект User
        User user = new User();
        user.setId(1L);

        // Создаем полноценный объект Notification
        Notification notification = new Notification();
        notification.setId(id);
        notification.setTitle("Тестовое уведомление");
        notification.setMessage("Тестовое сообщение");
        notification.setChannel(NotificationChannel.EMAIL);
        notification.setStatus(NotificationStatus.CREATED);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRecipient(user);

        when(notificationService.getNotificationById(id)).thenReturn(notification);

        notificationController.getNotificationById(id);

        verify(notificationService, times(1)).getNotificationById(id);
    }

    @Test
    void shouldGetNotificationByIdAndReturnDto() {
        Long id = 1L;

        // Создаем полноценный объект User
        User user = new User();
        user.setId(1L);
        user.setName("Иван Иванов");
        user.setEmail("ivan@example.com");

        // Создаем полноценный объект Notification
        Notification notification = new Notification();
        notification.setId(id);
        notification.setTitle("Тестовое уведомление");
        notification.setMessage("Тестовое сообщение");
        notification.setChannel(NotificationChannel.EMAIL);
        notification.setStatus(NotificationStatus.CREATED);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRecipient(user);

        when(notificationService.getNotificationById(id)).thenReturn(notification);

        var result = notificationController.getNotificationById(id);

        assertNotNull(result);
        assertEquals("Тестовое уведомление", result.getTitle());
        assertEquals("Тестовое сообщение", result.getMessage());
        assertEquals(NotificationChannel.EMAIL, result.getChannel());
        assertEquals(1L, result.getRecipientId());

        verify(notificationService, times(1)).getNotificationById(id);
    }

    @Test
    void shouldCallGetByStatus() {
        NotificationStatus status = NotificationStatus.CREATED;
        notificationController.getByStatus(status);
        verify(notificationService, times(1)).getNotificationsByStatus(status);
    }

    @Test
    void shouldCallGetByChannel() {
        NotificationChannel channel = NotificationChannel.EMAIL;
        notificationController.getByChannel(channel);
        verify(notificationService, times(1)).getNotificationsByChannel(channel);
    }

    @Test
    void shouldCallGetByRecipientId() {
        Long recipientId = 1L;
        notificationController.getByRecipientId(recipientId);
        verify(notificationService, times(1)).getNotificationsByRecipientId(recipientId);
    }

    @Test
    void shouldGetAllNotificationsWithData() {
        // Создаем пользователя
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        // Создаем уведомления
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setTitle("Уведомление 1");
        notification1.setMessage("Сообщение 1");
        notification1.setChannel(NotificationChannel.EMAIL);
        notification1.setStatus(NotificationStatus.CREATED);
        notification1.setCreatedAt(LocalDateTime.now());
        notification1.setRecipient(user1);

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setTitle("Уведомление 2");
        notification2.setMessage("Сообщение 2");
        notification2.setChannel(NotificationChannel.SMS);
        notification2.setStatus(NotificationStatus.SENT);
        notification2.setCreatedAt(LocalDateTime.now());
        notification2.setRecipient(user2);

        List<Notification> notifications = Arrays.asList(notification1, notification2);

        when(notificationService.getAllNotifications()).thenReturn(notifications);

        List<?> result = notificationController.getAllNotifications();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(notificationService, times(1)).getAllNotifications();
    }
}