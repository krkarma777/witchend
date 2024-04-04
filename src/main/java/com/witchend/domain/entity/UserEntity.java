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
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // 유저 아이디

    @Column(nullable = false)
    private String password; // 패스워드

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role = UserRole.ROLE_USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status = UserStatus.INACTIVE;

    public UserEntity(UserCreateRequestDTO requestDTO) {
        this.username = requestDTO.getUsername();
        this.password = requestDTO.getPassword();
        this.email = requestDTO.getEmail();
    }

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserEntity() {
    }

    public void update(UserUpdateRequestDTO requestDTO) {
        this.password = requestDTO.getNewPassword();
        this.email = requestDTO.getEmail();
    }
}
