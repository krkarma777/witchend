package com.witchend.domain.entity;

import com.witchend.domain.dto.user.UserCreateRequestDTO;
import com.witchend.domain.dto.user.UserUpdateRequestDTO;
import com.witchend.domain.enums.UserRole;
import com.witchend.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "Users")
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // User identifier

    @Column(nullable = false, unique = true)
    private String username; // User ID

    @Column(nullable = false)
    private String password; // Password

    @Column(nullable = false)
    private String nickname; // User nickname

    @Column(nullable = false, unique = true)
    private String email; // Email

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role = UserRole.ROLE_USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status = UserStatus.INACTIVE;

    // Constructor based on UserCreateRequestDTO
    public UserEntity(UserCreateRequestDTO requestDTO) {
        this.nickname = requestDTO.getNickname();
        this.username = requestDTO.getUsername();
        this.password = requestDTO.getPassword();
        this.email = requestDTO.getEmail();
    }

    // Constructor accepting user information directly
    public UserEntity(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public UserEntity() {
    }

    // Update method based on UserUpdateRequestDTO
    public void update(UserUpdateRequestDTO requestDTO) {
        this.nickname = requestDTO.getNickname();
        this.password = requestDTO.getNewPassword();
        this.email = requestDTO.getEmail();
    }
}
