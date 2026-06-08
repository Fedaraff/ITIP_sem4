package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public UserDto createUser(@RequestBody @Valid UserDto request) {
        User response = userService.createUser(request);
        return mapToDto(response);
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User response = userService.getUserById(id);
        return mapToDto(response);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody @Valid UserDto request) {
        User response = userService.updateUser(id, request);
        return mapToDto(response);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return String.format("Пользователь %s удален", id);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .telegramChatId(user.getTelegramChatId())
                .deviceToken(user.getDeviceToken())
                .createdAt(user.getCreatedAt())
                .build();
    }
}