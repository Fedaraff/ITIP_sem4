package org.example;

import org.example.config.AnotherConfig;
import org.example.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AppConfig.class, AnotherConfig.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}