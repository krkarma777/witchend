package com.witchend.domain;

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

}
