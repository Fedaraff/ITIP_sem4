package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationManager {
    private final MessageService messageService;

    @Autowired
    public NotificationManager(MessageService messageService) {
        this.messageService = messageService;
    }

    public void notify(String message, String recipient) {
        messageService.sendMessage(message, recipient);
    }
}