package org.example.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    @NotBlank(message = "Заголовок не может быть пустым")
    private String title;

    @NotBlank(message = "Сообщение не может быть пустым")
    private String message;

    @NotNull(message = "Канал уведомления обязателен")
    private NotificationChannel channel;

    private NotificationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime sentAt;

    @NotNull(message = "ID получателя обязателен")
    private Long recipientId;
}