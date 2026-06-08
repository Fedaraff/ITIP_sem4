package org.example.controller;

import org.example.service.NotificationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org. springframework . context . ApplicationContext ;


@RestController
public class NotificationController {

    private final NotificationManager notificationManager;

    public NotificationController(NotificationManager notificationManager){
        this.notificationManager = notificationManager;
    }

    @GetMapping("/notify")
    public String notifyB(@RequestParam String message,
                          @RequestParam String email) {
        notificationManager.notify(message, email);
        return "Уведомление отправлено через Java Config";
    }
}
