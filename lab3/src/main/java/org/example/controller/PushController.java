package org.example.controller;

import org.example.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class PushController {

    private final List<MessageService> messageServices;

    public PushController(List<MessageService> messageServices) {
        this.messageServices = messageServices;
    }

    @GetMapping("/send")
    public String sendToAll(@RequestParam String message, @RequestParam String recipient) {
        messageServices.forEach(service ->
            service.sendMessage(message, recipient)
        );
        return "Уведомление отправлено через все доступные сервисы. Всего сервисов: " + messageServices.size();
    }
}