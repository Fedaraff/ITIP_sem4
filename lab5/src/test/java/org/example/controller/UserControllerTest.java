package org.example.controller;

import org.example.mapper.UserMapper;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.security.CustomUserDetailsService;
import org.example.security.JwtAuthenticationFilter;
import org.example.security.JwtService;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@WithMockUser(roles = "ADMIN")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    // Добавьте эти моки для security
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldGetAllUsers() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Иван");

        UserDto dto = UserDto.builder()
                .name("Иван")
                .email("ivan@example.com")
                .build();

        List<User> users = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.toDto(any(User.class))).thenReturn(dto);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetUserById() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Иван");

        UserDto dto = UserDto.builder()
                .name("Иван")
                .email("ivan@example.com")
                .build();

        when(userService.getUserById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserDto dto = UserDto.builder()
                .name("Новый пользователь")
                .email("new@example.com")
                .build();

        User user = new User();
        user.setName(dto.getName());

        when(userService.createUser(any(UserDto.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(dto);

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content("{\"name\":\"Новый пользователь\",\"email\":\"new@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }
}