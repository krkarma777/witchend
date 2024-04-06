package com.witchend.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDTO {

    @NotEmpty
    private String username; // User ID

    @NotEmpty
    private String password; // Password

    @NotEmpty
    private String nickname; // User nickname

    @NotEmpty
    @Email
    private String email; // Email address

    public UserCreateRequestDTO() {
    }

    // Constructor to initialize UserCreateRequestDTO with parameters
    public UserCreateRequestDTO(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
}
