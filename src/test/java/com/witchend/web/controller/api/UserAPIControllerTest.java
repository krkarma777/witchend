package com.witchend.web.controller.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.witchend.domain.dto.user.UserCreateRequestDTO;
import com.witchend.domain.dto.user.UserUpdateRequestDTO;
import com.witchend.domain.entity.User;
import com.witchend.domain.sevice.user.UserService;
import com.witchend.domain.validator.user.UserAuthValidator;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private UserAuthValidator userAuthValidator;

    private User mockUser;


    @BeforeEach
    public void setUp() {
        mockUser = new User("userTest", "Password123!", "nickname", "user@test.com");
        mockUser.setId(1L);
        mockUser.setUsername("testUser");
        mockUser.setEmail("user@witchend.com");
    }

    @Test
    public void whenRegisterUser_thenSaveUser() throws Exception {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("userTest", "Password123!", "nickname", "user@test.com");

        when(userService.existsByUsername(anyString())).thenReturn(false);
        when(userService.existsByEmail(anyString())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");

        // When & Then
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("회원가입이 정상적으로 완료되었습니다."));
    }

    @Test
    public void whenRegisterUserWithExistingUsername_thenConflict() throws Exception {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(
                "existingUser",
                "Password123!",
                "test",
                "user@test.com"
        );

        when(userService.existsByUsername(requestDTO.getUsername())).thenReturn(true);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 사용중인 아이디입니다."));
    }

    @Test
    public void whenRegisterUserWithExistingEmail_thenConflict() throws Exception {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(
                "userTest",
                "Password123!",
                "test",
                "existing@test.com"
        );

        when(userService.existsByUsername(requestDTO.getUsername())).thenReturn(false);
        when(userService.existsByEmail(requestDTO.getEmail())).thenReturn(true);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 사용중인 이메일입니다."));
    }
    @Test
    public void whenRegisterUserWithExistingNickname_thenConflict() throws Exception {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(
                "userTest",
                "Password123!",
                "existingNickname",
                "user@test.com"
        );

        when(userService.existsByUsername(requestDTO.getUsername())).thenReturn(false);
        when(userService.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(userService.existsByNickname(requestDTO.getNickname())).thenReturn(true);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 사용중인 닉네임입니다."));
    }

    @Test
    public void whenRegisterUserWithInvalidPassword_thenBadRequest() throws Exception {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("userTest", "pass", "nickname", "user@test.com");

        when(userService.existsByUsername(requestDTO.getUsername())).thenReturn(false);
        when(userService.existsByEmail(requestDTO.getEmail())).thenReturn(false);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("비밀번호는 영문, 숫자, 특수문자의 조합으로 8~20자여야 합니다."));
    }

    @Test
    @WithMockUser
    void update_Successful() throws Exception {
        UserUpdateRequestDTO updateRequestDTO = new UserUpdateRequestDTO();
        updateRequestDTO.setOriginalPassword("Password123!");
        updateRequestDTO.setEmail("test@witchend.com");
        updateRequestDTO.setNewPassword("ChangePassword1!");

        when(userAuthValidator.getCurrentUser(any(Principal.class))).thenReturn(mockUser);
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(patch("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원 수정이 완료되었습니다."));
    }

    @Test
    @WithMockUser
    void update_WithWrongOriginalPassword_ThenUnauthorized() throws Exception {
        UserUpdateRequestDTO updateRequestDTO = new UserUpdateRequestDTO();
        updateRequestDTO.setOriginalPassword("WrongPassword123!");
        updateRequestDTO.setEmail("test@witchend.com");
        updateRequestDTO.setNewPassword("ChangePassword1!");

        when(userAuthValidator.getCurrentUser(any(Principal.class))).thenReturn(mockUser);
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(patch("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("입력하신 기존 비밀번호가 맞지 않습니다."));
    }
}