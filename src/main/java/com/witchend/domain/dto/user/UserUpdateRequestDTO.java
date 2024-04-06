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
    private String nickname; // User nickname

    @NotEmpty
    private String originalPassword; // Verification of the original password

    @NotEmpty
    private String newPassword; // New password

    @NotEmpty
    @Email
    private String email; // Email address
}

