package com.witchend.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDTO {

    @NotEmpty
    private String username; // 유저 아이디

    @NotEmpty
    private String password; // 패스워드

    @NotEmpty
    @Email
    private String email; // 이메일

    public UserCreateRequestDTO() {
    }

    public UserCreateRequestDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
