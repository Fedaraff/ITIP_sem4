package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.mapper.UserMapper;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return userMapper.toDto(user);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return userMapper.toDto(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userDto);
        return userMapper.toDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}