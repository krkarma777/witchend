package com.witchend.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateRequestDTO {

    @NotEmpty
    private String originalPassword; // 기존 비밀번호 검증

    @NotEmpty
    private String newPassword; // 새로운 비밀번호

    @NotEmpty
    @Email
    private String email; // 이메일

}
