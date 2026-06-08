package org.example.config;

import org.example.service.PushService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnotherConfig {

    @Bean
    public PushService pushService() {
        System.out.println("Создание PushService через AnotherConfig");
        return new PushService();
    }
}