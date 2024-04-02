package com.witchend.domain.entity;

import com.witchend.domain.UserCreateRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Users")
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

    public UserEntity(UserCreateRequestDTO requestDTO) {
        this.username = requestDTO.getUsername();
        this.password = requestDTO.getPassword();
        this.email = requestDTO.getEmail();
    }

    public UserEntity() {
    }
}
