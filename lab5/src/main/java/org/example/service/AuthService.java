package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.RegisterRequest;
import org.example.model.entity.User;
import org.example.model.enums.UserRole;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        // Проверка уникальности email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с email " + request.getEmail() + " уже существует");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    // Добавляем метод для регистрации администратора
    public void registerAdmin(RegisterRequest request) {
        // Проверка уникальности email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с email " + request.getEmail() + " уже существует");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ROLE_ADMIN);  // Назначаем роль ADMIN
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}