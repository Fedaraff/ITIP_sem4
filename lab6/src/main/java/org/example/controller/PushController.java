package org.example.controller;

import org.example.service.NotificationManager;
import org.example.service.PushService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushController {

    private final NotificationManager notificationManager;

    public PushController(ApplicationContext context) {
        PushService pushService = context.getBean(PushService.class);
        this.notificationManager = new NotificationManager(pushService);
    }

    @GetMapping("/push")
    public String sendPush(@RequestParam String message,
                           @RequestParam String deviceId) {
        notificationManager.notify(message, deviceId);
        return "Push уведомление отправлено через AnotherConfig";
    }
}