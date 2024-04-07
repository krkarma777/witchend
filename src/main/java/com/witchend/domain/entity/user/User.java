package com.witchend.domain.entity.user;

import com.witchend.domain.dto.user.UserCreateRequestDTO;
import com.witchend.domain.dto.user.UserUpdateRequestDTO;
import com.witchend.domain.enums.UserRole;
import com.witchend.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {

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

    // User가 가질 수 있는 GameCharacter 리스트
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameCharacter> gameCharacters = new ArrayList<>();

    public User(UserCreateRequestDTO requestDTO) {
        this.nickname = requestDTO.getNickname();
        this.username = requestDTO.getUsername();
        this.password = requestDTO.getPassword();
        this.email = requestDTO.getEmail();
    }

    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public User() {
    }

    // Update method based on UserUpdateRequestDTO
    public void update(UserUpdateRequestDTO requestDTO) {
        this.nickname = requestDTO.getNickname();
        this.password = requestDTO.getNewPassword();
        this.email = requestDTO.getEmail();
    }
}
